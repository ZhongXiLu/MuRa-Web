package com.github.muraweb;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.LoggerContext;
import core.MutantExporter;
import core.RankedMutant;
import core.ReportGenerator;
import core.rankers.ck.CKRanker;
import core.rankers.complexity.ComplexityRanker;
import core.rankers.history.HistoryRanker;
import core.rankers.impact.CoverageRanker;
import core.rankers.impact.ImpactRanker;
import core.rankers.usage.UsageRanker;
import lumutator.Configuration;
import lumutator.Mutant;
import org.eclipse.jgit.api.Git;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.ConfigurationSetup;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;

import static pitest.Parser.getMutantsWithMutantType;

@Controller
public class AnalysisController {

    @GetMapping("/analysis")
    public String getForm() {
        return "analysis";
    }

    @PostMapping("/analysis")
    public String postForm(@ModelAttribute Analysis analysis, Model model) {

        try {
            // Clone project
            final File projectDir = new File("repos" + File.separator + analysis.hashCode());
            if (!projectDir.exists()) {
                projectDir.mkdirs();
                Git.cloneRepository()
                        .setURI(analysis.getGitDir())
                        .setDirectory(projectDir)
                        .call();
            }

            // Initial configuration for PITest
            Configuration config = Configuration.getInstance();
            Configuration.getInstance().initialize("config.xml");
            config.set("projectDir", projectDir.getCanonicalPath());
            ConfigurationSetup.addPITest(new File(config.get("projectDir") + "/pom.xml"));
            if (!analysis.isSingleModule()) config.set("sourcePath", analysis.getModule() + "/src/main/java/");

            // Run mutation testing with PITest
            ProcessBuilder processBuilder = null;
            if (!analysis.isSingleModule()) {
                processBuilder = new ProcessBuilder(
                        "mvn", "clean", "test", "-pl", analysis.getModule(),
                        "-Dfeatures=+EXPORT", "org.pitest:pitest-maven:mutationCoverage"
                );
            } else {
                processBuilder = new ProcessBuilder(
                        "mvn", "clean", "test",
                        "-Dfeatures=+EXPORT", "org.pitest:pitest-maven:mutationCoverage"
                );
            }
            processBuilder.directory(new File(config.get("projectDir")));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }  // read output from buffer, otherwise buffer might get full
            process.waitFor();
            reader.close();
            if (process.exitValue() != 0) {
                throw new RuntimeException("One of the tests failed while applying mutation analysis");
            }

            // Parse PITest mutants
            List<Mutant> mutants = getMutantsWithMutantType(
                    Paths.get(config.get("projectDir"),
                            analysis.isSingleModule() ? "" : analysis.getModule(),
                            "target", "pit-reports").toString(),
                    true, RankedMutant.class
            );

            // Setup configuration for MuRa
            if (!analysis.isSingleModule()) {
                // Append module to paths
                config.set("classFiles", projectDir + "/" + analysis.getModule() + "/target/classes");
                config.set("classPath", projectDir + "/" + analysis.getModule() + "/target/classes/:" + projectDir + "/" + analysis.getModule() + "/target/test-classes/");
                config.set("sourcePath", projectDir + "/" + analysis.getModule() + "/src/main/java/");
                config.set("testDir", projectDir + "/" + analysis.getModule() + "/src/test/java/");
            } else {
                config.set("classFiles", projectDir + "/target/classes");
                config.set("classPath", projectDir + "/target/classes/:" + projectDir + "/target/test-classes/");
                config.set("sourcePath", projectDir + "/src/main/java/");
                config.set("testDir", projectDir + "/src/test/java/");
            }
            ConfigurationSetup.addClassPath(config, analysis.isSingleModule() ? "" : analysis.getModule());
            BasicConfigurator basicConfigurator = new BasicConfigurator();
            basicConfigurator.configure(new LoggerContext());

            // Call MuRa rankers
            if (analysis.isCK()) CKRanker.rankCK(mutants, config.get("sourcePath"));
            if (analysis.isCC()) ComplexityRanker.rank(mutants, config.get("classFiles"));
            if (analysis.isUSG()) UsageRanker.rank(mutants, config.get("classFiles"));
            if (analysis.isH() && new File(config.get("projectDir") + File.separator + ".git").exists()) {
                HistoryRanker.rank(mutants);
            }
            if (analysis.isLC()) CoverageRanker.rank(mutants, config.get("classFiles"));
            if (analysis.isIMP()) ImpactRanker.rank(mutants, config.get("classFiles"));

            // Generate the report
            ReportGenerator.generateReport(mutants, config.get("projectDir") + "/report.html");

            // Also export the mutants with their scores
            MutantExporter.exportMutantsToCSV(mutants, config.get("projectDir") + "/mutants.csv");

        } catch (Exception e) {
            // TODO: return exception to view?
            e.printStackTrace();
        }

        return "analysis";  // TODO: show new page?
    }

}

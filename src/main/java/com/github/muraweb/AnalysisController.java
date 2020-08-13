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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AnalysisRepository analysisRepository;

    @GetMapping("/analysis")
    public String getForm() {
        return "analysis";
    }

    @PostMapping("/analysis")
    public String postForm(@ModelAttribute AnalysisForm analysisForm, Model model) {

        Analysis analysis = new Analysis();

        try {
            // Clone project
            analysis.setGitRepo(analysisForm.getGitRepo());
            final File projectDir = new File("repos" + File.separator + analysisForm.hashCode());
            if (!projectDir.exists()) {
                projectDir.mkdirs();
                Git.cloneRepository()
                        .setURI(analysisForm.getGitRepo())
                        .setDirectory(projectDir)
                        .call();
            }

            // Initial configuration for PITest
            Configuration config = Configuration.getInstance();
            Configuration.getInstance().initialize("config.xml");
            config.set("projectDir", projectDir.getCanonicalPath());
            ConfigurationSetup.addPITest(new File(config.get("projectDir") + "/pom.xml"));
            if (!analysisForm.isSingleModule()) config.set("sourcePath", analysisForm.getModule() + "/src/main/java/");

            // Run mutation testing with PITest
            ProcessBuilder processBuilder = null;
            if (!analysisForm.isSingleModule()) {
                processBuilder = new ProcessBuilder(
                        "mvn", "clean", "test", "-pl", analysisForm.getModule(),
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
            while ((reader.readLine()) != null) {
            }  // read output from buffer, otherwise buffer might get full
            process.waitFor();
            reader.close();
            if (process.exitValue() != 0) {
                throw new RuntimeException("One of the tests failed while applying mutation analysis");
            }

            // Parse PITest mutants
            List<Mutant> mutants = getMutantsWithMutantType(
                    Paths.get(config.get("projectDir"),
                            analysisForm.isSingleModule() ? "" : analysisForm.getModule(),
                            "target", "pit-reports").toString(),
                    true, RankedMutant.class
            );

            // Setup configuration for MuRa
            if (!analysisForm.isSingleModule()) {
                // Append module to paths
                config.set("classFiles", projectDir + "/" + analysisForm.getModule() + "/target/classes");
                config.set("classPath", projectDir + "/" + analysisForm.getModule() + "/target/classes/:" + projectDir + "/" + analysisForm.getModule() + "/target/test-classes/");
                config.set("sourcePath", projectDir + "/" + analysisForm.getModule() + "/src/main/java/");
                config.set("testDir", projectDir + "/" + analysisForm.getModule() + "/src/test/java/");
            } else {
                config.set("classFiles", projectDir + "/target/classes");
                config.set("classPath", projectDir + "/target/classes/:" + projectDir + "/target/test-classes/");
                config.set("sourcePath", projectDir + "/src/main/java/");
                config.set("testDir", projectDir + "/src/test/java/");
            }
            ConfigurationSetup.addClassPath(config, analysisForm.isSingleModule() ? "" : analysisForm.getModule());
            BasicConfigurator basicConfigurator = new BasicConfigurator();
            basicConfigurator.configure(new LoggerContext());

            // Call MuRa rankers
            if (analysisForm.isCK()) CKRanker.rankCK(mutants, config.get("sourcePath"));
            if (analysisForm.isCC()) ComplexityRanker.rank(mutants, config.get("classFiles"));
            if (analysisForm.isUSG()) UsageRanker.rank(mutants, config.get("classFiles"));
            if (analysisForm.isH() && new File(config.get("projectDir") + File.separator + ".git").exists()) {
                HistoryRanker.rank(mutants);
            }
            if (analysisForm.isLC()) CoverageRanker.rank(mutants, config.get("classFiles"));
            if (analysisForm.isIMP()) ImpactRanker.rank(mutants, config.get("classFiles"));

            // Generate the report
            ReportGenerator.generateReport(mutants, config.get("projectDir") + "/report.html");
            analysis.setReport(config.get("projectDir") + "/report.html");

            // Also export the mutants with their scores
            MutantExporter.exportMutantsToCSV(mutants, config.get("projectDir") + "/mutants.csv");
            analysis.setMutants(config.get("projectDir") + "/mutants.csv");

            analysisRepository.save(analysis);

        } catch (Exception e) {
            // TODO: return exception to view?
            e.printStackTrace();
        }

        return "analysis";  // TODO: show new page?
    }

}

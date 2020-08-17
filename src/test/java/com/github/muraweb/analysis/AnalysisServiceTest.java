package com.github.muraweb.analysis;

import lumutator.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@DataJpaTest
class AnalysisServiceTest {

    @Autowired
    private AnalysisRepository analysisRepository;

    private AnalysisService analysisService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        analysisService = new AnalysisService(analysisRepository);
    }

    @Test
    public void startAnalysisTest() throws IOException {
        AnalysisForm analysisForm = new AnalysisForm();
        analysisForm.setGitRepo("https://github.com/slub/urnlib");
        analysisForm.setSingleModule(true);
        analysisForm.setCK(false);
        analysisForm.setCC(false);
        analysisForm.setUSG(false);
        analysisForm.setH(true);
        analysisForm.setLC(true);
        analysisForm.setIMP(true);
        analysisForm.setOptimalWeights(false);

        // Provide a custom config file for this test
        Configuration config = spy(Configuration.class);
        doNothing().when(config).initialize(anyString());
        Configuration.getInstance().initialize(getClass().getClassLoader().getResource("config.xml").getFile());

        String outDir = Files.createTempDirectory("out").toString();
        analysisService.startAnalysis(analysisForm, outDir);

        // Check if the necessary files are generated
        assertTrue(new File(outDir + "/slub/urnlib/report.html").exists());
        assertTrue(new File(outDir + "/slub/urnlib/mutants.csv").exists());

        // Also test multi-module project
//        analysisForm.setGitRepo("https://github.com/OpenFeign/feign");
//        analysisForm.setSingleModule(false);
//        analysisForm.setModule("core");
//        analysisForm.setCK(true);
//        analysisForm.setCC(true);
//        analysisForm.setUSG(true);
//        analysisForm.setH(false);
//        analysisForm.setLC(false);
//        analysisForm.setIMP(false);
//        analysisForm.setOptimalWeights(true);
//
//        Configuration.getInstance().initialize(getClass().getClassLoader().getResource("config.xml").getFile());
//        analysisService.startAnalysis(analysisForm, outDir);
//
//        assertTrue(new File(outDir + "/OpenFeign/feign/report.html").exists());
//        assertTrue(new File(outDir + "/OpenFeign/feign/mutants.csv").exists());

        System.out.println("Analyses present: " + analysisService.getAllAnalyses().size());
    }

}
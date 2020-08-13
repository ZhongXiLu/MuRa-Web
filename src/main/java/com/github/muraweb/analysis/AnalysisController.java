package com.github.muraweb.analysis;

import com.github.muraweb.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Collections;
import java.util.List;

@Controller
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    /**
     * Directory to store the results of the analysis.
     */
    final static String outputDir = "results";

    /**
     * Get an analysis form.
     *
     * @return The analysis form.
     */
    @GetMapping("/analysis")
    public String getForm() {
        return "analysisForm";
    }

    /**
     * Create a new request for a repository to be analyzed by MuRa.
     */
    @PostMapping("/analysis")
    public String postForm(@ModelAttribute AnalysisForm analysisForm, RedirectAttributes attr) {
        analysisService.startAnalysis(analysisForm, outputDir);
        attr.addFlashAttribute("message",
                "Successfully started an analysis for " + Util.getRepoName(analysisForm.getGitRepo()) + "!"
        );
        return "redirect:/";    // redirect to homepage
    }

    /**
     * Get all the analyses.
     */
    @GetMapping("/")
    public String getAnalyses(Model model) {
        List<Analysis> analyses = analysisService.getAllAnalyses();
        Collections.reverse(analyses);
        model.addAttribute("analyses", analyses);
        return "index2";
    }

    /**
     * Get the details of an analysis.
     *
     * @param repoName The name of the repository that was analyzed.
     * @return The analysis.
     */
    @GetMapping("/analysis/{repoOwner}/{repoName}")
    @Deprecated
    public String getAnalysis(Model model, @PathVariable String repoOwner, @PathVariable String repoName) {
        model.addAttribute("analysis", analysisService.getAnalysisByRepoName(repoOwner + "/" + repoName));
        return "analysis";
    }

    /**
     * Retrieve a file from the server.
     */
    @GetMapping("/analysis/{repoOwner}/{repoName}/{filename:.+}")
    @ResponseBody
    public FileSystemResource getFile(
            HttpServletResponse response,
            @PathVariable String repoOwner, @PathVariable String repoName, @PathVariable String filename
    ) {
        final File file = new File(outputDir + "/" + repoOwner + "/" + repoName + "/" + filename);
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);
        return new FileSystemResource(file);
    }

}

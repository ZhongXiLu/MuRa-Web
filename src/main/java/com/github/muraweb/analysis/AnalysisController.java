package com.github.muraweb.analysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

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
    public String postForm(@ModelAttribute AnalysisForm analysisForm) {
        analysisService.startAnalysis(analysisForm, outputDir);
        return "analysisForm";  // TODO: return to homepage?
    }

    /**
     * Get the details of an analysis.
     *
     * @param repoName The name of the repository that was analyzed.
     * @return The analysis.
     */
    @GetMapping("/analysis/{repoOwner}/{repoName}")
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

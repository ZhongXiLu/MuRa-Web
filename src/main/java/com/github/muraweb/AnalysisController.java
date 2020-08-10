package com.github.muraweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnalysisController {

    @GetMapping("/analysis")
    public String getForm() {
        return "analysis";
    }

    @PostMapping("/analysis")
    public String postForm(@ModelAttribute Analysis analysis, Model model) {
        return "analysis";
    }

}

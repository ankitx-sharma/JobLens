package com.project.joblens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.joblens.dto.JobAnalysisForm;
import com.project.joblens.dto.JobAnalysisView;
import com.project.joblens.service.JobAnalysisService;

import jakarta.validation.Valid;

@Controller
public class JobAnalysisController {
	
	@Autowired
	private JobAnalysisService service;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("jobAnalysisForm", new JobAnalysisForm());
		return "home";
	}
	
	@PostMapping("/analyze")
	public String analyze(@Valid @ModelAttribute("jobAnalysisForm") JobAnalysisForm form,
																	BindingResult bindinResult,
																	Model model) {
		if(bindinResult.hasErrors()) {
			return "home";
		}
		
		JobAnalysisView result = service.analyze(form);
		model.addAttribute("result", result);
		
		return "result";
	}
}

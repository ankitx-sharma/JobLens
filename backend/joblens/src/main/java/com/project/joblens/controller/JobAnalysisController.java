package com.project.joblens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.project.joblens.ai.service.JobAnalysisStreamingService;
import com.project.joblens.dto.JobAnalysisForm;

import jakarta.validation.Valid;

@Controller
public class JobAnalysisController {

	@Autowired
	private JobAnalysisStreamingService streamingService;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("jobAnalysisForm", new JobAnalysisForm());
		return "home";
	}

	@PostMapping("/analyze")
	public String analyze(@Valid @ModelAttribute("jobAnalysisForm") JobAnalysisForm form,
			BindingResult bindinResult,
			Model model) {
		if (bindinResult.hasErrors()) {
			return "home";
		}

		String jobDescription = form.getJobDescriptionText().trim();
		model.addAttribute("jobDescriptionText", jobDescription);
		model.addAttribute("jobDescriptionPreview",
				jobDescription.length() > 300 ? jobDescription.substring(0, 300) + "..." : jobDescription);

		return "result";
	}

	@PostMapping(path = "/analyze/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public SseEmitter streamAnalysis(@RequestParam("jobDescriptionText") String jobDescriptionText) {
		return streamingService.streamAnalysis(jobDescriptionText);
	}
}

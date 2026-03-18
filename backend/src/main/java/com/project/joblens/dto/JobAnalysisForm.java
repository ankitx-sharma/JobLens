package com.project.joblens.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class JobAnalysisForm {
	@NotBlank(message="Job description cannot be empty.")
	@Size(min = 100, message="Please paste a fuller job description (minimum 100 characters).")
	private String jobDescriptionText;
	
	public void setJobDescriptionText(String jobDescriptionText) {
		this.jobDescriptionText = jobDescriptionText;
	}
	
	public String getJobDescriptionText() {
		return this.jobDescriptionText;
	}
}

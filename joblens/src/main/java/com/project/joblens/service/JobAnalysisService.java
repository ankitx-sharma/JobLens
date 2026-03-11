package com.project.joblens.service;

import org.springframework.stereotype.Service;

import com.project.joblens.dto.JobAnalysisForm;
import com.project.joblens.dto.JobAnalysisView;

@Service
public class JobAnalysisService {
	public JobAnalysisView analyze(JobAnalysisForm form) {
		String preview = buildPreview(form.getJobDescriptionText());
		
		JobAnalysisView response = new JobAnalysisView();
		response.setJobDescriptionText(preview);
		response.setRoleInterpretation("""
                        Role: Backend Java Developer
                        Seniority: Mid-level to Senior
                        Primary technologies:
                        • Java
                        • Spring Boot
                        • REST APIs
                        Engineering focus:
                        • backend service development
                        • scalable APIs
                        • production-grade systems
                        """);
		response.setIndustryDomain("""
                        Domain: Technology / Software
                        Signals detected:
                        • backend development
                        • service design
                        • engineering collaboration
                        Implications:
                        This role appears to focus on building and maintaining software systems.
                        """);
		response.setProductSystemType("""
                        Product / System Type:
                        • service-oriented backend platform
                        • API-driven application
                        Architecture signals:
                        • distributed service communication
                        • scalable backend design
                        """);
		response.setTeamEnvironment("""
                        Team & Work Environment:
                        • collaborative engineering team
                        • likely agile setup
                        • ownership of implementation tasks
                        Language / communication:
                        • professional communication expected
                        """);
		
		return response;
	}
	
	private String buildPreview(String input) {
		String trimmed = input == null ? "" : input.trim();
		return trimmed.length() > 300 ? trimmed.substring(0, 300) + "..." : trimmed;
	}
}

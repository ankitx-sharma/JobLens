package com.project.joblens.service;

import org.springframework.stereotype.Service;

@Service
public class PromptBuilderService {
	
	public String buildJobAnalysisPrompt(String jobDescriptionText) {
		return """
				You are analyzing a software engineering job description.

                Your task is to return a structured analysis using exactly these 4 sections and headings:

                ROLE INTERPRETATION
                INDUSTRY / DOMAIN
                PRODUCT / SYSTEM ARCHITECTURE
                TEAM & WORK ENVIRONMENT

                Rules:
                - Be specific and concise.
                - Do not write generic advice.
                - Base the analysis only on the provided job description.
                - If something is unclear, say "Not clearly specified".
                - Use short bullet points where useful.
                - Keep each section readable and well-structured.

                For each section, include:

                ROLE INTERPRETATION
                - Role type
                - Seniority level
                - Primary technologies
                - Engineering focus

                INDUSTRY / DOMAIN
                - Domain or industry
                - Signals detected
                - What this implies

                PRODUCT / SYSTEM ARCHITECTURE
                - Product or system type
                - Architecture signals
                - Technical/system characteristics

                TEAM & WORK ENVIRONMENT
                - Team characteristics
                - Collaboration style
                - Language or communication expectations
                - Work style

                Job Description:
                --------------------
                %s
                --------------------
				""".formatted(jobDescriptionText);
	}

}

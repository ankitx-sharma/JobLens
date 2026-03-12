package com.project.joblens.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.joblens.ai.AiAnalysisService;
import com.project.joblens.dto.JobAnalysisForm;
import com.project.joblens.dto.JobAnalysisView;
import com.project.joblens.entity.JobAnalysisRequestEntity;
import com.project.joblens.entity.JobAnalysisResultEntity;
import com.project.joblens.repository.JobAnalysisRequestRepository;
import com.project.joblens.repository.JobAnalysisResultRepository;

@Service
public class JobAnalysisService {
	
	@Autowired
	private JobAnalysisRequestRepository requestRepository;
	
	@Autowired
	private JobAnalysisResultRepository resultRepository;
	
	@Autowired
	private PromptBuilderService promptBuilder;
	
	@Autowired
	private AiAnalysisService aiService;
	
	@Transactional
	public JobAnalysisView analyze(JobAnalysisForm form) {
		String jobDescriptionPreview = form.getJobDescriptionText().trim();
		
		JobAnalysisRequestEntity requestEntity = new JobAnalysisRequestEntity();
		requestEntity.setJobDescriptionText(form.getJobDescriptionText());
		
		requestEntity = requestRepository.save(requestEntity);
		
		String prompt = promptBuilder.buildJobAnalysisPrompt(jobDescriptionPreview);
		String rawAiResponse = aiService.analyzeJobDescription(prompt);
		
		Map<String, String> parsedSections = parseSections(rawAiResponse);
		
		String roleInterpretation = parsedSections.getOrDefault("ROLE INTERPRETATION", "Not available");
		String industryDomain = parsedSections.getOrDefault("INDUSTRY / DOMAIN", "Not available");
		String productSystemType = parsedSections.getOrDefault("PRODUCT / SYSTEM ARCHITECTURE", "Not available");
		String teamEnvironment = parsedSections.getOrDefault("TEAM & WORK ENVIRONMENT", "Not available");
		
		JobAnalysisResultEntity resultEntity = new JobAnalysisResultEntity();
		resultEntity.setRequest(requestEntity);
		resultEntity.setRoleInterpretation(roleInterpretation);
		resultEntity.setIndustryDomain(industryDomain);
		resultEntity.setProductSystemType(productSystemType);
		resultEntity.setRawAiResponse(rawAiResponse);
		resultEntity.setTeamEnvironment(teamEnvironment);
		
		resultEntity = resultRepository.save(resultEntity);
		
		JobAnalysisView response = new JobAnalysisView();
		response.setRequestId(requestEntity.getId());
		response.setResultId(resultEntity.getId());
		response.setJobDescriptionPreview(buildPreview(jobDescriptionPreview));
		response.setIndustryDomain(industryDomain);
		response.setProductSystemType(productSystemType);
		response.setRoleInterpretation(roleInterpretation);
		response.setTeamEnvironment(teamEnvironment);
		
		return response;
	}
	
	private String buildPreview(String input) {
		String trimmed = input == null ? "" : input.trim();
		return trimmed.length() > 300 ? trimmed.substring(0, 300) + "..." : trimmed;
	}
	
	private Map<String, String> parseSections(String rawResponse) {
		var sections = new LinkedHashMap<String, String>();
		String[] headings = {
				"ROLE INTERPRETATION",
                "INDUSTRY / DOMAIN",
                "PRODUCT / SYSTEM ARCHITECTURE",
                "TEAM & WORK ENVIRONMENT"
		};
		
		for(int i=0; i<headings.length; i++) {
			String heading = headings[i];
			int start = rawResponse.indexOf(heading);
			
			if(start == -1) {
				continue;
			}
			
			int contentStart = start + heading.length();
			int end = rawResponse.length();
			
			if(i+1 < headings.length) {
				int nextStart = rawResponse.indexOf(headings[i+1], contentStart);
				if(nextStart != -1) {
					end = nextStart;
				}
			}
			
			String content = rawResponse.substring(contentStart, end).trim();
			sections.put(heading, content);
		}
		
		return sections;
	}
}

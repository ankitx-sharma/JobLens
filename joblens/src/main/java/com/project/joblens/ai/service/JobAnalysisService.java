package com.project.joblens.ai.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.joblens.ai.helper.JobAnalysisDataHelper;
import com.project.joblens.ai.prompt.PromptBuilderService;
import com.project.joblens.ai.prompt.PromptVersion;
import com.project.joblens.ai.provider.AiModelClientService;
import com.project.joblens.dto.JobAnalysisForm;
import com.project.joblens.dto.JobAnalysisView;
import com.project.joblens.dto.StructuredJobAnalysisDto;
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
	private ObjectMapper objectMapper;
	
	@Autowired
	private AiModelClientService aiService;
	
	@Transactional
	public JobAnalysisView analyze(JobAnalysisForm form) {
		String jobDescriptionPreview = form.getJobDescriptionText().trim();
		
		JobAnalysisRequestEntity requestEntity = new JobAnalysisRequestEntity();
		requestEntity.setJobDescriptionText(form.getJobDescriptionText());
		
		requestEntity = requestRepository.save(requestEntity);
		
		String prompt = promptBuilder.buildStructuredJsonPrompt(jobDescriptionPreview, PromptVersion.V1);
		String rawAiResponse = aiService.analyzeJobDescription(prompt);
		
		System.out.println("RAW AI RESPONSE:");
		System.out.println(rawAiResponse);
		
		StructuredJobAnalysisDto parsed;
		
		try {
			String cleanedJson = JobAnalysisDataHelper.extractJson(rawAiResponse);
			parsed = objectMapper.readValue(cleanedJson, StructuredJobAnalysisDto.class);
		} catch(Exception ex) {
			throw new RuntimeException("Failed to parse AI response as JSON: " + rawAiResponse, ex);
		}
		
		JobAnalysisResultEntity resultEntity = new JobAnalysisResultEntity();
		resultEntity.setRequest(requestEntity);
		resultEntity.setRoleInterpretation(JobAnalysisDataHelper.toActualRoleText(parsed));
		resultEntity.setIndustryDomain(JobAnalysisDataHelper.toThemesText(parsed.getDominantThemes()));
		resultEntity.setProductSystemType(JobAnalysisDataHelper.toStrategicInterpretationText(parsed));
		resultEntity.setRawAiResponse(rawAiResponse);
		resultEntity.setTeamEnvironment(JobAnalysisDataHelper.toPositioningAdviceText(parsed));
		
		resultEntity = resultRepository.save(resultEntity);
		
		JobAnalysisView response = new JobAnalysisView();
		response.setRequestId(requestEntity.getId());
		response.setResultId(resultEntity.getId());
		response.setJobDescriptionPreview(JobAnalysisDataHelper.buildPreview(jobDescriptionPreview));
		response.setActualRole(parsed.getActualRole());
		response.setDominantThemes(JobAnalysisDataHelper.safeThemes(parsed.getDominantThemes()));
		response.setStrategicInterpretation(parsed.getStrategicInterpretation());
		response.setPositioningAdvice(parsed.getPositioningAdvice());
		
		return response;
	}
}

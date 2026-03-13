package com.project.joblens.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.joblens.ai.AiAnalysisService;
import com.project.joblens.dto.DominantThemeDto;
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
	private AiAnalysisService aiService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Transactional
	public JobAnalysisView analyze(JobAnalysisForm form) {
		String jobDescriptionPreview = form.getJobDescriptionText().trim();
		
		JobAnalysisRequestEntity requestEntity = new JobAnalysisRequestEntity();
		requestEntity.setJobDescriptionText(form.getJobDescriptionText());
		
		requestEntity = requestRepository.save(requestEntity);
		
		String prompt = promptBuilder.buildStructuredJsonPrompt(jobDescriptionPreview);
		String rawAiResponse = aiService.analyzeJobDescription(prompt);
		
//		System.out.println("RAW AI RESPONSE:");
//		System.out.println(rawAiResponse);
		
		StructuredJobAnalysisDto parsed;
		
		try {
			String cleanedJson = extractJson(rawAiResponse);
			parsed = objectMapper.readValue(cleanedJson, StructuredJobAnalysisDto.class);
		} catch(Exception ex) {
			throw new RuntimeException("Failed to parse AI response as JSON: " + rawAiResponse, ex);
		}
		
		JobAnalysisResultEntity resultEntity = new JobAnalysisResultEntity();
		resultEntity.setRequest(requestEntity);
		resultEntity.setRoleInterpretation(toActualRoleText(parsed));
		resultEntity.setIndustryDomain(toThemesText(parsed.getDominantThemes()));
		resultEntity.setProductSystemType(toStrategicInterpretationText(parsed));
		resultEntity.setRawAiResponse(rawAiResponse);
		resultEntity.setTeamEnvironment(toPositioningAdviceText(parsed));
		
		resultEntity = resultRepository.save(resultEntity);
		
		JobAnalysisView response = new JobAnalysisView();
		response.setRequestId(requestEntity.getId());
		response.setResultId(resultEntity.getId());
		response.setJobDescriptionPreview(buildPreview(jobDescriptionPreview));
		response.setActualRole(parsed.getActualRole());
		response.setDominantThemes(safeThemes(parsed.getDominantThemes()));
		response.setStrategicInterpretation(parsed.getStrategicInterpretation());
		response.setPositioningAdvice(parsed.getPositioningAdvice());
		
		return response;
	}
	
	private List<DominantThemeDto> safeThemes(List<DominantThemeDto> themes) {
        return themes != null ? themes : Collections.emptyList();
    }
	
	private String extractJson(String rawResponse) {
	    if (rawResponse == null || rawResponse.isBlank()) {
	        throw new RuntimeException("AI response is empty.");
	    }

	    int start = rawResponse.indexOf("{");
	    int end = rawResponse.lastIndexOf("}");

	    if (start == -1 || end == -1 || start >= end) {
	        throw new RuntimeException("No valid JSON object found in AI response: " + rawResponse);
	    }

	    return rawResponse.substring(start, end + 1);
	}
	
	private String toActualRoleText(StructuredJobAnalysisDto parsed) {
        if (parsed == null || parsed.getActualRole() == null) {
            return "";
        }
        return "Headline: " + nullSafe(parsed.getActualRole().getHeadline())
                + "\nExplanation: " + nullSafe(parsed.getActualRole().getExplanation());
    }
	
	private String toThemesText(List<DominantThemeDto> themes) {
        if (themes == null || themes.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < themes.size(); i++) {
            DominantThemeDto theme = themes.get(i);
            sb.append("Theme ").append(i + 1).append(": ").append(nullSafe(theme.getThemeTitle())).append("\n")
              .append("Keywords: ").append(theme.getKeywords() != null ? theme.getKeywords() : Collections.emptyList()).append("\n")
              .append("Meaning: ").append(nullSafe(theme.getMeaning())).append("\n\n");
        }
        return sb.toString().trim();
    }
	
	private String toStrategicInterpretationText(StructuredJobAnalysisDto parsed) {
        if (parsed == null || parsed.getStrategicInterpretation() == null) {
            return "";
        }
        return "What company likely values: "
                + (parsed.getStrategicInterpretation().getWhatCompanyLikelyValues() != null
                ? parsed.getStrategicInterpretation().getWhatCompanyLikelyValues()
                : Collections.emptyList())
                + "\nWhat this usually means for candidates: "
                + nullSafe(parsed.getStrategicInterpretation().getWhatThisUsuallyMeansForCandidates());
    }
	
	private String toPositioningAdviceText(StructuredJobAnalysisDto parsed) {
        if (parsed == null || parsed.getPositioningAdvice() == null) {
            return "";
        }
        return "What to emphasize: "
                + (parsed.getPositioningAdvice().getWhatToEmphasize() != null
                ? parsed.getPositioningAdvice().getWhatToEmphasize()
                : Collections.emptyList())
                + "\nHow to think about missing skills: "
                + nullSafe(parsed.getPositioningAdvice().getHowToThinkAboutMissingSkills());
    }
	
	private String nullSafe(String value) {
        return value != null ? value : "";
    }
	
	private String buildPreview(String input) {
		String trimmed = input == null ? "" : input.trim();
		return trimmed.length() > 300 ? trimmed.substring(0, 300) + "..." : trimmed;
	}
}

package com.project.joblens.ai.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.joblens.ai.helper.IncrementalAnalysisAssembler;
import com.project.joblens.ai.prompt.PromptBuilderService;
import com.project.joblens.ai.prompt.PromptVersion;
import com.project.joblens.ai.provider.AiModelClientService;
import com.project.joblens.entity.JobAnalysisRequestEntity;
import com.project.joblens.entity.JobAnalysisResultEntity;
import com.project.joblens.repository.JobAnalysisRequestRepository;
import com.project.joblens.repository.JobAnalysisResultRepository;

import reactor.core.Disposable;

@Service
public class JobAnalysisStreamingService {
	
	@Autowired
	private JobAnalysisRequestRepository requestRepository;

	@Autowired
	private JobAnalysisResultRepository resultRepository;

	@Autowired
	private PromptBuilderService promptBuilder;

	@Autowired
	private AiModelClientService aiService;
	
	@Autowired
	private ObjectMapper objectMapper;

	public SseEmitter streamAnalysis(String jobDescription) {
		if (jobDescription == null || jobDescription.isBlank()) {
			throw new IllegalArgumentException("Job description must not be empty.");
		}

		String trimmedJobDescription = jobDescription.trim();
		JobAnalysisRequestEntity requestEntity = new JobAnalysisRequestEntity();
		requestEntity.setJobDescriptionText(trimmedJobDescription);
		requestEntity = requestRepository.save(requestEntity);

		String prompt = promptBuilder.buildStructuredJsonPrompt(trimmedJobDescription, PromptVersion.V1);
		
		SseEmitter emitter = new SseEmitter(0L);
		StringBuilder fullRawResponse = new StringBuilder();
		IncrementalAnalysisAssembler assembler = new IncrementalAnalysisAssembler(objectMapper);
		JobAnalysisRequestEntity savedRequestEntity = requestEntity;

		Disposable subscription = aiService.streamJobDescriptionAnalysis(prompt)
				.subscribe(chunk -> {
					fullRawResponse.append(chunk);
					assembler.appendChunk(chunk);
					
					for(IncrementalAnalysisAssembler.StreamSectionEvent event: assembler.extractReadyEvents()) {
						sendEvent(emitter, event.getEventName(), event.getPayload());
					}
					
				}, error -> {
					sendEvent(emitter, "error", "Streaming failed: " + error.getMessage());
					emitter.completeWithError(error);
				}, () -> finalizeStream(emitter, savedRequestEntity, fullRawResponse.toString()));

		emitter.onCompletion(subscription::dispose);
		emitter.onTimeout(() -> {
			subscription.dispose();
			emitter.complete();
		});
		emitter.onError(ex -> subscription.dispose());

		return emitter;
	}

	private void finalizeStream(
			SseEmitter emitter,
			JobAnalysisRequestEntity requestEntity,
			String rawAiResponse) {
		try {
			persistStreamingResult(requestEntity, rawAiResponse);
			sendEvent(emitter, "done", "{\"status\":\"done\"}");
			emitter.complete();
		} catch (Exception ex) {
			sendEvent(emitter, "error", "Failed to save the streamed analysis.");
			emitter.completeWithError(ex);
		}
	}

	private void persistStreamingResult(JobAnalysisRequestEntity requestEntity,
			String rawAiResponse) {
		JobAnalysisResultEntity resultEntity = new JobAnalysisResultEntity();
		resultEntity.setRequest(requestEntity);
		resultEntity.setRoleInterpretation("");
		resultEntity.setIndustryDomain("");
		resultEntity.setProductSystemType("");
		resultEntity.setTeamEnvironment("");
		resultEntity.setRawAiResponse(rawAiResponse);
		resultRepository.save(resultEntity);
	}

	private void sendEvent(SseEmitter emitter, String eventName, Object data) {
		try {
			emitter.send(SseEmitter.event().name(eventName).data(data));
		} catch (IOException ex) {
			emitter.completeWithError(ex);
		}
	}
}

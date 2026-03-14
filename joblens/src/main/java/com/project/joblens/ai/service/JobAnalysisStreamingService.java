package com.project.joblens.service;

import org.springframework.stereotype.Service;

@Service
public class JobAnalysisStreamingService {
//	
//	@Autowired
//	private PromptBuilderService promptService;
//	
//	@Autowired
//	private AiAnalysisService aiService;
//	
//	public SseEmitter streamAnalysis(String jobDescription) {
//		SseEmitter emitter = new SseEmitter(0L);
//		
//		String prompt = promptService.buildStreamingPrompt(jobDescription);
//		StringBuilder buffer = new StringBuilder();
//		
//		Disposable subscription = aiService.streamJobDescriptionAnalysis(prompt)
//				.subscribe(chunk -> {
//					synchronized (buffer) {
//						buffer.append(chunk);
//						
//						if(shouldFlush(buffer)) {
//							sendChunk(emitter, buffer.toString());
//							buffer.setLength(0);
//						}
//					}
//				},
//					error -> sendError(emitter, error),
//						   () -> {
//							   synchronized (buffer) {
//								if(!buffer.isEmpty()) {
//									sendChunk(emitter, buffer.toString());
//								}
//							}
//							   complete(emitter);
//						});
//		
//		emitter.onCompletion(subscription::dispose);
//		emitter.onTimeout(() -> {
//			subscription.dispose();
//			emitter.complete();
//		});
//		emitter.onError(ex -> subscription.dispose());
//		
//		return emitter;
//	}
//	
//	private boolean shouldFlush(StringBuilder buffer) {
//		if(buffer.isEmpty()) {
//			return false;
//		}
//		
//		String text = buffer.toString();
//		return text.endsWith(" ")
//				|| text.endsWith("\n")
//				|| text.endsWith(".")
//				|| text.endsWith(",")
//				|| text.endsWith("•")
//				|| text.length() > 40;
//	}
//	
//	private void sendChunk(SseEmitter emitter, String chunk) {
//		try {
//			emitter.send(SseEmitter.event()
//								.name("chunk")
//								.data(chunk));
//		} catch(IOException ex) {
//			emitter.completeWithError(ex);
//		}
//	}
//	
//	private void sendError(SseEmitter emitter, Throwable error) {
//		try {
//			emitter.send(SseEmitter.event()
//								.name("error")
//								.data("Streaming failed: "+error.getMessage()));
//		} catch(IOException ignored) {}
//		emitter.completeWithError(error);
//	}
//	
//	private void complete(SseEmitter emitter) {
//		try {
//			emitter.send(SseEmitter.event()
//								.name("done")
//								.data("[DONE]"));
//		} catch(IOException ignored) {}
//		emitter.complete();
//	}
}

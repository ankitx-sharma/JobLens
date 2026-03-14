package com.project.joblens.ai;

import reactor.core.publisher.Flux;

public interface AiAnalysisService {
	String analyzeJobDescription(String prompt);
	Flux<String> streamJobDescriptionAnalysis(String prompt);
}

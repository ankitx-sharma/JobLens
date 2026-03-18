package com.project.joblens.ai.provider;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class AiModelClientService implements AiModelClient{
	
	@Autowired
	@Qualifier("llamaChatClient")
	private ChatClient chatClient;
	
	@Override
	public String analyzeJobDescription(String prompt) {
		return chatClient.prompt().user(prompt).call().content();
	}

	public Flux<String> streamJobDescriptionAnalysis(String prompt) {
		return chatClient.prompt().user(prompt).stream().content();
	}
}

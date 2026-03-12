package com.project.joblens.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OllamaAnalysisService implements AiAnalysisService{

	@Autowired
	private ChatClient chatClient;
	
	@Override
	public String analyzeJobDescription(String prompt) {
		return chatClient.prompt().user(prompt).call().content();
	}

}

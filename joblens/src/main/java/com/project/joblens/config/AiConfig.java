package com.project.joblens.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
	
	@Bean
	@Qualifier("llamaChatClient")
	public ChatClient llamaChatClient(OllamaChatModel ollamaChatModel) {
		return ChatClient.builder(ollamaChatModel).build();
	}
	
	@Bean
	@Qualifier("mistralChatClient")
	public ChatClient mistralChatClient(MistralAiChatModel mistralaiChatModel) {
		return ChatClient.builder(mistralaiChatModel).build();
	}
	
	@Bean
	@Qualifier("gptChatClient")
	public ChatClient gptChatClient(OpenAiChatModel openAiChatModel) {
		return ChatClient.builder(openAiChatModel).build();
	}
}

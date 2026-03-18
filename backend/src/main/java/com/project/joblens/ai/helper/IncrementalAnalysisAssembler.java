package com.project.joblens.ai.helper;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.joblens.dto.ActualRoleDto;
import com.project.joblens.dto.DominantThemeDto;
import com.project.joblens.dto.PositioningAdviceDto;
import com.project.joblens.dto.StrategicInterpretationDto;

public class IncrementalAnalysisAssembler {
	
	private final StringBuilder buffer = new StringBuilder();
	private final ObjectMapper objectMapper;
	
	public IncrementalAnalysisAssembler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	public void appendChunk(String chunk) {
		if(chunk != null) {
			buffer.append(chunk);
		}
	}
	
	public List<StreamSectionEvent> extractReadyEvents() {
		List<StreamSectionEvent> events = new ArrayList<>();
		
		String content = buffer.toString();
		String[] lines = content.split("\\R", -1);
		
		// keep last incomplete line in buffer
		buffer.setLength(0);
		if(!content.endsWith("\n") && !content.endsWith("\r")) {
			buffer.append(lines[lines.length - 1]);
		}
		
		int completeLineCount = content.endsWith("\n") || content.endsWith("\r")
								? lines.length
								: lines.length - 1;
		
		for(int i=0; i<completeLineCount; i++) {
			String line = lines[i].trim();
			if(line.isBlank()) {
				continue;
			}
		
			try {
				JsonNode root = objectMapper.readTree(line);
				String type = root.path("type").asText();
				
				switch (type) {
					case "actualRole" -> {
						ActualRoleDto dto = objectMapper.treeToValue(root.path("data"), ActualRoleDto.class);
						events.add(new StreamSectionEvent("actualRole", dto));
					}
					case "dominantTheme" -> {
						int index = root.path("index").asInt();
						DominantThemeDto dto = objectMapper.treeToValue(root.path("data"), DominantThemeDto.class);
						events.add(new StreamSectionEvent("dominantTheme", new DominantThemeEventPayload(index, dto)));
					}
					case "strategicInterpretation" -> {
						StrategicInterpretationDto dto = objectMapper.treeToValue(root.path("data"), StrategicInterpretationDto.class);
						events.add(new StreamSectionEvent("strategicInterpretation", dto));
					}
					case "positioningAdvice" -> {
						PositioningAdviceDto dto = objectMapper.treeToValue(root.path("data"), PositioningAdviceDto.class);
						events.add(new StreamSectionEvent("positioningAdvice", dto));
					}
					default -> {}
				}
			} catch(Exception ex) {}
		}
		
		return events;
	}
	
	public static class StreamSectionEvent {
		private final String eventName;
		private final Object payload;
		
		public StreamSectionEvent(String eventName, Object payload) {
			this.eventName = eventName;
			this.payload = payload;
		}
		
		public String getEventName() {
			return this.eventName;
		}
		public Object getPayload() {
			return this.payload;
		}
	}
	
	public static class DominantThemeEventPayload {
		private final int index;
		private final DominantThemeDto data;
		
		public DominantThemeEventPayload(int index, DominantThemeDto data) {
			this.index = index;
			this.data = data;
		}
		
		public int getIndex() {
			return this.index;
		}
		public DominantThemeDto getData() {
			return this.data;
		}
	}
}

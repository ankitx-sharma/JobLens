package com.project.joblens.dto;

import java.util.List;

public class StructuredJobAnalysisDto {
	private ActualRoleDto actualRole;
	private List<DominantThemeDto> dominantThemes;
	private StrategicInterpretationDto strategicInterpretation;
	private PositioningAdviceDto positioningAdvice;
	
	public ActualRoleDto getActualRole() {
		return actualRole;
	}
	public void setActualRole(ActualRoleDto actualRole) {
		this.actualRole = actualRole;
	}
	public List<DominantThemeDto> getDominantThemes() {
		return dominantThemes;
	}
	public void setDominantThemes(List<DominantThemeDto> dominantThemes) {
		this.dominantThemes = dominantThemes;
	}
	public StrategicInterpretationDto getStrategicInterpretation() {
		return strategicInterpretation;
	}
	public void setStrategicInterpretation(StrategicInterpretationDto strategicInterpretation) {
		this.strategicInterpretation = strategicInterpretation;
	}
	public PositioningAdviceDto getPositioningAdvice() {
		return positioningAdvice;
	}
	public void setPositioningAdvice(PositioningAdviceDto positioningAdvice) {
		this.positioningAdvice = positioningAdvice;
	}
}

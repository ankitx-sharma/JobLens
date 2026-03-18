package com.project.joblens.dto;

import java.util.List;

public class JobAnalysisView {
	private Long requestId;
	private Long resultId;
	
	private String jobDescription;
	private String jobDescriptionPreview;
	private String jobDescriptionTruncated;
	
	private ActualRoleDto actualRole;
	private List<DominantThemeDto> dominantThemes;
	private StrategicInterpretationDto strategicInterpretation;
	private PositioningAdviceDto positioningAdvice;
	
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public Long getResultId() {
		return resultId;
	}
	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getJobDescriptionPreview() {
		return jobDescriptionPreview;
	}
	public void setJobDescriptionPreview(String jobDescriptionPreview) {
		this.jobDescriptionPreview = jobDescriptionPreview;
	}
	public String getJobDescriptionTruncated() {
		return jobDescriptionTruncated;
	}
	public void setJobDescriptionTruncated(String jobDescriptionTruncated) {
		this.jobDescriptionTruncated = jobDescriptionTruncated;
	}
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

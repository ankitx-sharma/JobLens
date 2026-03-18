package com.project.joblens.dto;

import java.util.List;

public class AnalysisSectionDto {
	private List<String> explicitSignals;
	private String interpretation;
	private String whyThisMattersForCandidate;
	private String confidence;
	
	public List<String> getExplicitSignals() {
		return explicitSignals;
	}
	public void setExplicitSignals(List<String> explicitSignals) {
		this.explicitSignals = explicitSignals;
	}
	public String getInterpretation() {
		return interpretation;
	}
	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}
	public String getWhyThisMattersForCandidate() {
		return whyThisMattersForCandidate;
	}
	public void setWhyThisMattersForCandidate(String whyThisMattersForCandidate) {
		this.whyThisMattersForCandidate = whyThisMattersForCandidate;
	}
	public String getConfidence() {
		return confidence;
	}
	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}
}

package com.project.joblens.dto;

import java.util.List;

public class StrategicInterpretationDto {
	private List<String> whatCompanyLikelyValues;
	private String whatThisUsuallyMeansForCandidates;
	
	public List<String> getWhatCompanyLikelyValues() {
		return whatCompanyLikelyValues;
	}
	public void setWhatCompanyLikelyValues(List<String> whatCompanyLikelyValues) {
		this.whatCompanyLikelyValues = whatCompanyLikelyValues;
	}
	public String getWhatThisUsuallyMeansForCandidates() {
		return whatThisUsuallyMeansForCandidates;
	}
	public void setWhatThisUsuallyMeansForCandidates(String whatThisUsuallyMeansForCandidates) {
		this.whatThisUsuallyMeansForCandidates = whatThisUsuallyMeansForCandidates;
	}
}

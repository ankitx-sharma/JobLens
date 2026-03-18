package com.project.joblens.dto;

import java.util.List;

public class PositioningAdviceDto {
	private List<String> whatToEmphasize;
	private String howToThinkAboutMissingSkills;
	
	public List<String> getWhatToEmphasize() {
		return whatToEmphasize;
	}
	public void setWhatToEmphasize(List<String> whatToEmphasize) {
		this.whatToEmphasize = whatToEmphasize;
	}
	public String getHowToThinkAboutMissingSkills() {
		return howToThinkAboutMissingSkills;
	}
	public void setHowToThinkAboutMissingSkills(String howToThinkAboutMissingSkills) {
		this.howToThinkAboutMissingSkills = howToThinkAboutMissingSkills;
	}
}

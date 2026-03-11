package com.project.joblens.dto;

public class JobAnalysisView {
	private String id;
	private String jobDescriptionText;
	private String roleInterpretation;
	private String industryDomain;
	private String productSystemType;
	private String teamEnvironment;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getJobDescriptionText() {
		return jobDescriptionText;
	}
	public void setJobDescriptionText(String jobDescriptionText) {
		this.jobDescriptionText = jobDescriptionText;
	}
	
	public String getRoleInterpretation() {
		return roleInterpretation;
	}
	public void setRoleInterpretation(String roleInterpretation) {
		this.roleInterpretation = roleInterpretation;
	}
	
	public String getIndustryDomain() {
		return industryDomain;
	}
	public void setIndustryDomain(String industryDomain) {
		this.industryDomain = industryDomain;
	}
	
	public String getProductSystemType() {
		return productSystemType;
	}
	public void setProductSystemType(String productSystemType) {
		this.productSystemType = productSystemType;
	}
	
	public String getTeamEnvironment() {
		return teamEnvironment;
	}
	public void setTeamEnvironment(String teamEnvironment) {
		this.teamEnvironment = teamEnvironment;
	}
}

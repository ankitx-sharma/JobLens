package com.project.joblens.dto;

public class JobAnalysisView {
	private Long requestId;
	private Long resultId;
	
	private String jobDescriptionPreview;
	private String roleInterpretation;
	private String industryDomain;
	private String productSystemType;
	private String teamEnvironment;
	
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
	
	public String getJobDescriptionPreview() {
		return jobDescriptionPreview;
	}
	public void setJobDescriptionPreview(String jobDescriptionPreview) {
		this.jobDescriptionPreview = jobDescriptionPreview;
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

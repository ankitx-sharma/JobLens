package com.project.joblens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_analysis_result")
public class JobAnalysisResultEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "request_id", nullable = false, unique = true)
	private JobAnalysisRequestEntity request;
	
	@Column(name = "role_interpretation", columnDefinition = "TEXT")
	private String roleInterpretation;
	
	@Column(name = "industry_domain", columnDefinition = "TEXT")
	private String industryDomain;
	
	@Column(name = "product_system_type", columnDefinition = "TEXT")
	private String productSystemType;
	
	@Column(name = "team_environment", columnDefinition = "TEXT")
	private String teamEnvironment;
	
	@Column(name = "raw_ai_response", columnDefinition = "TEXT")
	private String rawAiResponse;
	
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public JobAnalysisRequestEntity getRequest() {
		return request;
	}

	public void setRequest(JobAnalysisRequestEntity request) {
		this.request = request;
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

	public String getRawAiResponse() {
		return rawAiResponse;
	}

	public void setRawAiResponse(String rawAiResponse) {
		this.rawAiResponse = rawAiResponse;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}

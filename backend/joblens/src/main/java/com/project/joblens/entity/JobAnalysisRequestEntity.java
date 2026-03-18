package com.project.joblens.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "job_analysis_request")
public class JobAnalysisRequestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "job_description_text", columnDefinition = "TEXT", nullable = false)
	private String jobDescriptionText;
	
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@OneToOne(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private JobAnalysisResultEntity result;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJobDescriptionText() {
		return jobDescriptionText;
	}

	public void setJobDescriptionText(String jobDescriptionText) {
		this.jobDescriptionText = jobDescriptionText;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public JobAnalysisResultEntity getResult() {
		return result;
	}

	public void setResult(JobAnalysisResultEntity result) {
		this.result = result;
	}
}

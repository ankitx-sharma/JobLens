package com.project.joblens.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "prompts")
public class PromptsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="prompt_name", nullable = false)
	private String promptName;
	
	@Column(nullable = false)
	private String version;
	
	@Column(name = "prompt_text", nullable = false, columnDefinition = "text")
	private String promptText;
	
	@Column(name = "is_active", nullable = false)
	private boolean active = true;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPromptName() {
		return promptName;
	}
	public void setPromptName(String promptName) {
		this.promptName = promptName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPromptText() {
		return promptText;
	}
	public void setPromptText(String promptText) {
		this.promptText = promptText;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}

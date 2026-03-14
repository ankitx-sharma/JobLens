package com.project.joblens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.joblens.entity.PromptsEntity;

@Repository
public interface PromptRepository extends JpaRepository<PromptsEntity, Long>{
	Optional<PromptsEntity> findByPromptNameAndVersionAndActiveTrue(String promptName, String version);
}

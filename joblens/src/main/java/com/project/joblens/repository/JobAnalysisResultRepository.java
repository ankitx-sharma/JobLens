package com.project.joblens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.joblens.entity.JobAnalysisResultEntity;

@Repository
public interface JobAnalysisResultRepository extends JpaRepository<JobAnalysisResultEntity, Long>{
}

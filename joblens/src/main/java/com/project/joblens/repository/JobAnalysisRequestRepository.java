package com.project.joblens.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.joblens.entity.JobAnalysisRequestEntity;

@Repository
public interface JobAnalysisRequestRepository extends JpaRepository<JobAnalysisRequestEntity, Long>{
}

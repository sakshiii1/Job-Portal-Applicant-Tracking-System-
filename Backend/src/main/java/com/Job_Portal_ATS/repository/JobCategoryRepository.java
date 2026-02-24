package com.Job_Portal_ATS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Job_Portal_ATS.entity.JobCategory;

public interface JobCategoryRepository
        extends JpaRepository<JobCategory, Long> {
}

package com.Job_Portal_ATS.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Job_Portal_ATS.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByRecruiterProfileRecruiterId(Long recruiterId);

}

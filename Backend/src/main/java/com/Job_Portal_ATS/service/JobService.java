package com.Job_Portal_ATS.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.Job_Portal_ATS.dto.JobDTO;
import com.Job_Portal_ATS.entity.Job;

public interface JobService {

//    Job createJob(JobDTO dto);

    List<Job> getAllJobs();

	Job createJob(JobDTO dto, Authentication authentication);

	void deleteJob(Long jobId, Authentication authentication);

	Job updateJob(Long jobId, JobDTO dto, Authentication authentication);
}

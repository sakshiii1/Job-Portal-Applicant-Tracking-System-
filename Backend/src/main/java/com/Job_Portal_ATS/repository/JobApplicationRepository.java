package com.Job_Portal_ATS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Job_Portal_ATS.entity.Job;
import com.Job_Portal_ATS.entity.JobApplication;
import com.Job_Portal_ATS.entity.User;

public interface JobApplicationRepository
extends JpaRepository<JobApplication, Long> {


	List<JobApplication> findByUser(User user);
	List<JobApplication> findByJobJobId(Long jobId);
	List<JobApplication> findByJob(Job job);
	



}

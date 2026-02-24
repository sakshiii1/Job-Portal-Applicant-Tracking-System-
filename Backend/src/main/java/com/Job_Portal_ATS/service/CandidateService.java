package com.Job_Portal_ATS.service;

import java.util.List;

import com.Job_Portal_ATS.dto.ApplicationDTO;
import com.Job_Portal_ATS.entity.CandidateProfile;

public interface CandidateService {
	
    // Apply Job
    ApplicationDTO applyJob(Long jobId, Long userId);

    // View Applied Jobs
    List<ApplicationDTO> getAppliedJobs(Long userId);

    // Withdraw Application
    String withdrawApplication(Long applicationId);
    
    void createProfile(Long userId, CandidateProfile profile);
}

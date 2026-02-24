package com.Job_Portal_ATS.service;

import java.util.List;

import com.Job_Portal_ATS.dto.ApplicationDTO;
import com.Job_Portal_ATS.dto.JobDTO;
import com.Job_Portal_ATS.entity.RecruiterProfile;

public interface RecruiterService {

    // ================= CREATE / UPDATE PROFILE =================
	RecruiterProfile createRecruiterProfile(String email, RecruiterProfile profile);


    // ================= GET PROFILE BY EMAIL =================
    RecruiterProfile getProfileByEmail(String email);

    // ================= CREATE JOB =================
    JobDTO createJob(JobDTO jobDTO);

    // ================= GET RECRUITER JOBS =================
    List<JobDTO> getRecruiterJobs(Long recruiterId);

    // ================= GET APPLICANTS BY JOB =================
    List<ApplicationDTO> getApplicantsByJob(Long jobId);

    // ================= UPDATE APPLICATION STATUS =================
    String updateApplicationStatus(Long applicationId,
                                   String status);
}

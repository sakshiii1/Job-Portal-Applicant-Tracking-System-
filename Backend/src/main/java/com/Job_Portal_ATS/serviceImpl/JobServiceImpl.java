package com.Job_Portal_ATS.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.Job_Portal_ATS.dto.JobDTO;
import com.Job_Portal_ATS.entity.ApprovalStatus;
import com.Job_Portal_ATS.entity.Job;
import com.Job_Portal_ATS.entity.JobCategory;
import com.Job_Portal_ATS.entity.RecruiterProfile;
import com.Job_Portal_ATS.entity.User;
import com.Job_Portal_ATS.repository.JobCategoryRepository;
import com.Job_Portal_ATS.repository.JobRepository;
import com.Job_Portal_ATS.repository.RecruiterProfileRepository;
import com.Job_Portal_ATS.repository.UserRepository;
import com.Job_Portal_ATS.service.JobService;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobCategoryRepository categoryRepository;

    @Autowired
    private RecruiterProfileRepository recruiterRepository;
    
    @Autowired
    private UserRepository userRepository;


    // ================= CREATE JOB =================

    @Override
    public Job createJob(JobDTO dto, Authentication authentication) {
    	
    	 String email = authentication.getName();
    	 
    	// RECRUITER FETCH
    	 User user = userRepository.findByEmail(email)
    		        .orElseThrow(() ->
    		                new RuntimeException("User not found"));

    		RecruiterProfile recruiter =
    		        recruiterRepository.findByUser(user)
    		        .orElseThrow(() ->
    		                new RuntimeException("Recruiter not found"));

        

        // 🔥 IMPORTANT APPROVAL CHECK
        if (recruiter.getApprovalStatus() != ApprovalStatus.APPROVED) {
            throw new RuntimeException("Recruiter not approved yet");
        }

        
        JobCategory category =
                categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setJobType(dto.getJobType());
        job.setJobStatus("OPEN");
        job.setCategory(category);
        job.setRecruiterProfile(recruiter);

        // CATEGORY SET
//        JobCategory category =
//                categoryRepository.findById(dto.getCategoryId())
//                .orElseThrow(() ->
//                        new RuntimeException("Category not found"));
//
//        job.setCategory(category);
//
//        job.setRecruiterProfile(recruiter);

        return jobRepository.save(job);
    }

    // ================= GET ALL JOBS =================

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    
    @Override
    public void deleteJob(Long jobId,
                          Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        RecruiterProfile recruiter =
                recruiterRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));


        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        if (!job.getRecruiterProfile()
                .getRecruiterId()
                .equals(recruiter.getRecruiterId())) {
            throw new RuntimeException("Unauthorized action");
        }

        jobRepository.delete(job);
    }
    
    
    @Override
    public Job updateJob(Long jobId,
                         JobDTO dto,
                         Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        RecruiterProfile recruiter =
                recruiterRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Recruiter not found"));


        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));

        // 🔥 Ownership Check
        if (!job.getRecruiterProfile()
                .getRecruiterId()
                .equals(recruiter.getRecruiterId())) {
            throw new RuntimeException("Unauthorized action");
        }

        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setJobType(dto.getJobType());

        return jobRepository.save(job);
    }


}


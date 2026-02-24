package com.Job_Portal_ATS.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Job_Portal_ATS.dto.ApplicationDTO;
import com.Job_Portal_ATS.entity.CandidateProfile;
import com.Job_Portal_ATS.entity.Job;
import com.Job_Portal_ATS.entity.JobApplication;
import com.Job_Portal_ATS.entity.User;
import com.Job_Portal_ATS.repository.CandidateProfileRepository;
import com.Job_Portal_ATS.repository.JobApplicationRepository;
import com.Job_Portal_ATS.repository.JobRepository;
import com.Job_Portal_ATS.repository.UserRepository;
import com.Job_Portal_ATS.service.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    // ================= APPLY JOB =================

    @Override
    public ApplicationDTO applyJob(Long jobId, Long userId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CandidateProfile profile = candidateProfileRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Candidate profile not found"));

        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setUser(user);
        application.setCandidateProfile(profile);
        application.setApplicationStatus("APPLIED");
        application.setAppliedDate(LocalDateTime.now());

        JobApplication saved = jobApplicationRepository.save(application);

        return mapToDTO(saved);
    }

    // ================= VIEW APPLIED JOBS =================

    @Override
    public List<ApplicationDTO> getAppliedJobs(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<JobApplication> applications =
                jobApplicationRepository.findByUser(user);

        return applications.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    // ================= WITHDRAW =================

    @Override
    public String withdrawApplication(Long applicationId) {

        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        application.setApplicationStatus("WITHDRAWN");

        jobApplicationRepository.save(application);

        return "Application Withdrawn Successfully";
    }

    // ================= DTO MAPPING =================

    private ApplicationDTO mapToDTO(JobApplication app) {

        ApplicationDTO dto = new ApplicationDTO();

        dto.setApplicationId(app.getApplicationId());
        dto.setJobId(app.getJob().getJobId());
        dto.setUserId(app.getUser().getId());
        dto.setStatus(app.getApplicationStatus());
        dto.setAppliedDate(app.getAppliedDate());

        return dto;
    }
    
    public void createProfile(Long userId, CandidateProfile profile) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        profile.setUser(user);

        candidateProfileRepository.save(profile);
    }

    
}

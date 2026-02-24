package com.Job_Portal_ATS.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Job_Portal_ATS.dto.ApplicationDTO;
import com.Job_Portal_ATS.dto.JobDTO;
import com.Job_Portal_ATS.entity.ApprovalStatus;
import com.Job_Portal_ATS.entity.Job;
import com.Job_Portal_ATS.entity.JobApplication;
import com.Job_Portal_ATS.entity.JobCategory;
import com.Job_Portal_ATS.entity.RecruiterProfile;
import com.Job_Portal_ATS.entity.User;
import com.Job_Portal_ATS.repository.JobApplicationRepository;
import com.Job_Portal_ATS.repository.JobCategoryRepository;
import com.Job_Portal_ATS.repository.JobRepository;
import com.Job_Portal_ATS.repository.RecruiterProfileRepository;
import com.Job_Portal_ATS.repository.UserRepository;
import com.Job_Portal_ATS.service.RecruiterService;

@Service
public class RecruiterServiceImpl implements RecruiterService {

    @Autowired
    private RecruiterProfileRepository recruiterRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private JobCategoryRepository categoryRepo;

    @Autowired
    private JobApplicationRepository applicationRepo;

    // ================= CREATE / UPDATE PROFILE =================
    @Override
    public RecruiterProfile createRecruiterProfile(String email, RecruiterProfile profile) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        profile.setUser(user);

        // ✅ IMPORTANT FIX
        profile.setApprovalStatus(ApprovalStatus.PENDING);


        return recruiterRepo.save(profile);
    }


    // ================= GET PROFILE BY USER ID =================
    @Override
    public RecruiterProfile getProfileByEmail(String email) {

    	User user = userRepo.findByEmail(email)
    	        .orElseThrow(() -> new RuntimeException("User not found"));

    	return recruiterRepo
    	        .findByUser(user)
    	        .orElseThrow(() -> new RuntimeException("Profile not found"));

    }


    // ================= CREATE JOB =================
    @Override
    public JobDTO createJob(JobDTO jobDTO) {

        RecruiterProfile recruiter =
                recruiterRepo.findById(jobDTO.getRecruiterId())
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        JobCategory category =
                categoryRepo.findById(jobDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setLocation(jobDTO.getLocation());
        job.setJobType(jobDTO.getJobType());
        job.setJobStatus("OPEN");
        job.setRecruiterProfile(recruiter);
        job.setCategory(category);

        Job saved = jobRepo.save(job);

        jobDTO.setJobId(saved.getJobId());
        jobDTO.setJobStatus(saved.getJobStatus());

        return jobDTO;
    }

    // ================= GET RECRUITER JOBS =================
    @Override
    public List<JobDTO> getRecruiterJobs(Long recruiterId) {

        List<Job> jobs =
                jobRepo.findByRecruiterProfileRecruiterId(recruiterId);

        List<JobDTO> dtoList = new ArrayList<>();

        for (Job job : jobs) {

            JobDTO dto = new JobDTO();
            dto.setJobId(job.getJobId());
            dto.setTitle(job.getTitle());
            dto.setDescription(job.getDescription());
            dto.setLocation(job.getLocation());
            dto.setJobType(job.getJobType());
            dto.setJobStatus(job.getJobStatus());

            dtoList.add(dto);
        }

        return dtoList;
    }

    // ================= GET APPLICANTS BY JOB =================
//    @Override
//    public List<ApplicationDTO> getApplicantsByJob(Long jobId) {
//
//        List<JobApplication> applications =
//                applicationRepo.findByJobJobId(jobId);
//
//        List<ApplicationDTO> dtoList = new ArrayList<>();
//
//        for (JobApplication app : applications) {
//
//            ApplicationDTO dto = new ApplicationDTO();
//
//            dto.setApplicationId(app.getApplicationId());
//            dto.setJobId(app.getJob().getJobId());
//            dto.setUserId(app.getUser().getId());
//            dto.setStatus(app.getApplicationStatus());
//            dto.setAppliedDate(app.getAppliedDate());
//
//            dtoList.add(dto);
//        }
//
//        return dtoList;
//    }
    @Override
    public List<ApplicationDTO> getApplicantsByJob(Long jobId) {

        System.out.println("JOB ID RECEIVED: " + jobId);

        List<JobApplication> applications =
                applicationRepo.findByJobJobId(jobId);

        System.out.println("APPLICATION COUNT: " + applications.size());

        List<ApplicationDTO> dtoList = new ArrayList<>();

        for (JobApplication app : applications) {

            ApplicationDTO dto = new ApplicationDTO();

            dto.setApplicationId(app.getApplicationId());
            dto.setJobId(app.getJob().getJobId());
            dto.setUserId(app.getUser().getId());
            dto.setStatus(app.getApplicationStatus());
            dto.setAppliedDate(app.getAppliedDate());

            dtoList.add(dto);
        }

        return dtoList;
    }

    // ================= UPDATE APPLICATION STATUS =================
    @Override
    public String updateApplicationStatus(Long applicationId,
                                          String status) {

        if (status == null || status.isBlank()) {
            throw new RuntimeException("Status cannot be null or empty");
        }

        JobApplication application =
                applicationRepo.findById(applicationId)
                .orElseThrow(() ->
                        new RuntimeException("Application not found"));

        application.setApplicationStatus(status.trim());

        applicationRepo.save(application);

        return "Application status updated to: " + status;
    }
}

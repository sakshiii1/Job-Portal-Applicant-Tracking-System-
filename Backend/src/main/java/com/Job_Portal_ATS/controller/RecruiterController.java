package com.Job_Portal_ATS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import com.Job_Portal_ATS.dto.ApplicationDTO;
import com.Job_Portal_ATS.dto.JobDTO;
import com.Job_Portal_ATS.entity.RecruiterProfile;
import com.Job_Portal_ATS.entity.User;
import com.Job_Portal_ATS.service.RecruiterService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recruiter")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    // ================= GET LOGGED-IN RECRUITER PROFILE =================

    @GetMapping("/profile")
    public ResponseEntity<RecruiterProfile> getRecruiterProfile(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        RecruiterProfile profile =
                recruiterService.getProfileByEmail(user.getEmail());

        return ResponseEntity.ok(profile);
    }

    // ================= CREATE RECRUITER PROFILE =================

    @PostMapping("/profile")
    public ResponseEntity<RecruiterProfile> createProfile(
            Authentication authentication,
            @RequestBody RecruiterProfile profile) {

        User user = (User) authentication.getPrincipal();

        RecruiterProfile saved =
                recruiterService.createRecruiterProfile(user.getEmail(), profile);

        return ResponseEntity.ok(saved);
    }

    // ================= CREATE JOB =================

    @PostMapping("/job")
    public ResponseEntity<JobDTO> createJob(
            @RequestBody JobDTO jobDTO) {

        JobDTO savedJob =
                recruiterService.createJob(jobDTO);

        return ResponseEntity.ok(savedJob);
    }

    // ================= GET RECRUITER JOBS =================

    @GetMapping("/jobs")
    public ResponseEntity<List<JobDTO>> getRecruiterJobs(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                recruiterService.getRecruiterJobs(user.getId())
        );
    }

    // ================= VIEW APPLICANTS BY JOB =================

    @GetMapping("/jobs/{jobId}/applications")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByJob(
            @PathVariable Long jobId) {

        return ResponseEntity.ok(
                recruiterService.getApplicantsByJob(jobId)
        );
    }

    // ================= UPDATE APPLICATION STATUS =================

    @PutMapping("/applications/{applicationId}/status")
    public ResponseEntity<String> updateApplicationStatus(
            @PathVariable Long applicationId,
            @RequestBody Map<String, String> body) {

        return ResponseEntity.ok(
                recruiterService.updateApplicationStatus(
                        applicationId,
                        body.get("status")));
    }
}
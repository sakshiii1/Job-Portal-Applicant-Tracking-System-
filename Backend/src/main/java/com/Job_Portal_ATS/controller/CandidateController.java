package com.Job_Portal_ATS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Job_Portal_ATS.dto.ApplicationDTO;
import com.Job_Portal_ATS.entity.CandidateProfile;
import com.Job_Portal_ATS.service.CandidateService;

@RestController
@RequestMapping("/api/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    // ================= APPLY JOB =================

    @PostMapping("/apply")
    public ResponseEntity<ApplicationDTO> applyJob(
            @RequestParam Long jobId,
            @RequestParam Long userId) {

        return ResponseEntity.ok(
                candidateService.applyJob(jobId, userId));
    }

    // ================= VIEW APPLIED =================

    @GetMapping("/applications/{userId}")
    public ResponseEntity<List<ApplicationDTO>> getAppliedJobs(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                candidateService.getAppliedJobs(userId));
    }

    // ================= WITHDRAW =================

    @PutMapping("/withdraw/{applicationId}")
    public ResponseEntity<String> withdrawApplication(
            @PathVariable Long applicationId) {

        return ResponseEntity.ok(
                candidateService.withdrawApplication(applicationId));
    }
    
 // ================= CREATE PROFILE =================

    @PostMapping("/profile")
    public ResponseEntity<String> createProfile(
            @RequestParam Long userId,
            @RequestBody CandidateProfile profile) {

        candidateService.createProfile(userId, profile);
        return ResponseEntity.ok("Candidate profile created successfully");
    }

}

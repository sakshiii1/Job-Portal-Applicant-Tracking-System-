package com.Job_Portal_ATS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Job_Portal_ATS.dto.JobDTO;
import com.Job_Portal_ATS.entity.Job;
import com.Job_Portal_ATS.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    // CREATE JOB
    @PostMapping
    public ResponseEntity<Job> createJob(
            @RequestBody JobDTO dto, Authentication authentication) {

        return ResponseEntity.ok(
                jobService.createJob(dto, authentication)
        );
    }
    
    // UPDATE JOB
    @PutMapping("/{jobId}")
    public ResponseEntity<?> updateJob(
            @PathVariable Long jobId,
            @RequestBody JobDTO dto,
            Authentication authentication) {

        return ResponseEntity.ok(
                jobService.updateJob(jobId, dto, authentication));
    }

    // GET ALL JOBS
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {

        return ResponseEntity.ok(
                jobService.getAllJobs()
        );
    }
    
    // DELETE JOB
    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> deleteJob(
            @PathVariable Long jobId,
            Authentication authentication) {

        jobService.deleteJob(jobId, authentication);
        return ResponseEntity.ok("Job deleted successfully");
    }
}

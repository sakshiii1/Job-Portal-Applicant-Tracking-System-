package com.Job_Portal_ATS.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    // ================= JOB RELATION =================

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @JsonBackReference(value = "job-application")   // 🔥 Stop Job loop
    private Job job;

    // ================= USER RELATION =================

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({
        "candidateProfile",
        "recruiterProfile",
        "jobApplications"
    })   // 🔥 Avoid full user recursion
    private User user;

    // ================= CANDIDATE PROFILE =================

    @ManyToOne
    @JoinColumn(name = "candidate_profile_id")
    @JsonBackReference(value = "candidate-application")
    private CandidateProfile candidateProfile;

    // ================= DETAILS =================

    @Column(nullable = false)
    private String applicationStatus;

    @Column(name = "applied_date")
    private LocalDateTime appliedDate;

    // ================= CONSTRUCTORS =================

    public JobApplication() {}

    public JobApplication(Job job,
                          User user,
                          CandidateProfile candidateProfile,
                          String applicationStatus) {
        this.job = job;
        this.user = user;
        this.candidateProfile = candidateProfile;
        this.applicationStatus = applicationStatus;
        this.appliedDate = LocalDateTime.now();
    }

    // ================= GETTERS / SETTERS =================

    public Long getApplicationId() {
        return applicationId;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CandidateProfile getCandidateProfile() {
        return candidateProfile;
    }

    public void setCandidateProfile(CandidateProfile candidateProfile) {
        this.candidateProfile = candidateProfile;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LocalDateTime getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(LocalDateTime appliedDate) {
        this.appliedDate = appliedDate;
    }

    // Wrapper (optional use in DTO mapping)

    public String getStatus() {
        return applicationStatus;
    }

    public void setStatus(String status) {
        this.applicationStatus = status;
    }
}

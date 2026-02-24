package com.Job_Portal_ATS.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;


import jakarta.persistence.*;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    private String title;
    private String description;
    private String location;
    private String jobType;
    private String jobStatus;   // OPEN / CLOSED

    // ================= RECRUITER RELATION =================

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    @JsonBackReference(value = "recruiter-job")   // 🔥 Stop recursion
    private RecruiterProfile recruiterProfile;

    // ================= CATEGORY RELATION =================

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference(value = "category-job")
    private JobCategory category;


    // ================= GETTERS & SETTERS =================

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public RecruiterProfile getRecruiterProfile() {
        return recruiterProfile;
    }

    public void setRecruiterProfile(RecruiterProfile recruiterProfile) {
        this.recruiterProfile = recruiterProfile;
    }

    public JobCategory getCategory() {
        return category;
    }

    public void setCategory(JobCategory category) {
        this.category = category;
    }
}

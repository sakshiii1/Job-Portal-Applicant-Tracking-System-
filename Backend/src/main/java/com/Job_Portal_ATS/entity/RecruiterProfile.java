package com.Job_Portal_ATS.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "recruiter_profiles")
public class RecruiterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruiterId;

    // ================= RELATIONSHIP =================
    // One Recruiter Profile belongs to One User

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference(value = "recruiter-user")   // 🔥 Fix recursion
    private User user;

    // ================= COMPANY DETAILS =================

    @Column(nullable = false)
    private String companyName;

    @Column(length = 2000)
    private String companyDescription;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
  
    // PENDING / APPROVED / REJECTED

    // ================= JOB RELATIONSHIP =================
    // One Recruiter → Many Jobs

    @OneToMany(mappedBy = "recruiterProfile",
               cascade = CascadeType.ALL)
    @JsonIgnore   // 🔥 Avoid infinite nesting
    private List<Job> jobs;

    // ================= CONSTRUCTORS =================

    public RecruiterProfile() {}

    public RecruiterProfile(User user,
            String companyName,
            String companyDescription,
            ApprovalStatus approvalStatus) {

this.user = user;
this.companyName = companyName;
this.companyDescription = companyDescription;
this.approvalStatus = approvalStatus;
}


    // ================= GETTERS & SETTERS =================

    public Long getRecruiterId() {
        return recruiterId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }


    public List<Job> getJobs() {
        return jobs;
    }
}

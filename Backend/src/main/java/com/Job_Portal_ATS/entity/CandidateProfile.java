package com.Job_Portal_ATS.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "candidate_profiles")
public class CandidateProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    // ================= RELATIONSHIP =================
    // One Candidate Profile belongs to One User

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonBackReference(value = "candidate-user")   // 🔥 Fix recursion
    private User user;

    // ================= CANDIDATE DETAILS =================

    @Column(length = 1000)
    private String skills;

    private Integer experienceYears;

    private String education;

    private String resumeLink;

    // ================= APPLICATION RELATION =================
    // One Candidate → Many Job Applications

    @OneToMany(mappedBy = "candidateProfile",
               cascade = CascadeType.ALL)
    @JsonIgnore   // 🔥 Avoid infinite nesting
    private List<JobApplication> jobApplications;

    // ================= CONSTRUCTORS =================

    public CandidateProfile() {}

    public CandidateProfile(User user,
                            String skills,
                            Integer experienceYears,
                            String education,
                            String resumeLink) {
        this.user = user;
        this.skills = skills;
        this.experienceYears = experienceYears;
        this.education = education;
        this.resumeLink = resumeLink;
    }

    // ================= GETTERS & SETTERS =================

    public Long getProfileId() {
        return profileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }
}

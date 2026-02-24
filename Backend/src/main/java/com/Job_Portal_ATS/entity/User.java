package com.Job_Portal_ATS.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String status;   // ACTIVE / BLOCKED

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    public ApprovalStatus getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(ApprovalStatus approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus;


    // ================= RELATIONSHIPS =================

    // 1️⃣ Candidate Profile
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "candidate-user")
    private CandidateProfile candidateProfile;

    // 2️⃣ Recruiter Profile
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "recruiter-user")
    private RecruiterProfile recruiterProfile;

    // 3️⃣ Job Applications
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore   // Avoid deep nesting
    private List<JobApplication> jobApplications;

    // ================= CONSTRUCTORS =================

    public User() {}

    public User(String name,
                String email,
                String password,
                Role role,
                String status) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public CandidateProfile getCandidateProfile() {
        return candidateProfile;
    }

    public RecruiterProfile getRecruiterProfile() {
        return recruiterProfile;
    }

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }
}

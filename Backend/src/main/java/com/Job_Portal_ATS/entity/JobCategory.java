package com.Job_Portal_ATS.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "job_categories")
public class JobCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false, unique = true)
    private String categoryName;

    @Column(length = 1000)
    private String description;

    // ================= RELATIONSHIP =================

    // One Category → Many Jobs
    @OneToMany(mappedBy = "category",
               cascade = CascadeType.ALL)
    @JsonManagedReference(value = "category-job")   // 🔥 FIX
    private List<Job> jobs;

    // ================= CONSTRUCTORS =================

    public JobCategory() {}

    public JobCategory(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    // ================= GETTERS & SETTERS =================

    public Long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Job> getJobs() {
        return jobs;
    }
}

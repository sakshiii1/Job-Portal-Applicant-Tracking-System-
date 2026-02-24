package com.Job_Portal_ATS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Job_Portal_ATS.dto.RecruiterApprovalDTO;
import com.Job_Portal_ATS.dto.UserDTO;
import com.Job_Portal_ATS.entity.JobCategory;
import com.Job_Portal_ATS.entity.RecruiterProfile;
import com.Job_Portal_ATS.repository.RecruiterProfileRepository;
import com.Job_Portal_ATS.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private RecruiterProfileRepository recruiterRepository;


    // ================= USERS =================

    // Get all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    // ================= RECRUITER APPROVAL =================

    // Approve / Reject recruiter
    @PutMapping("/recruiter/{id}/status")
    public ResponseEntity<String> updateRecruiterStatus(
            @PathVariable Long id,
            @RequestBody RecruiterApprovalDTO dto) {

        dto.setRecruiterId(id);
        String response = adminService.updateRecruiterStatus(dto);
        return ResponseEntity.ok(response);
    }



    // ================= JOB CATEGORY =================

    // Create category
    @PostMapping("/category")
    public ResponseEntity<JobCategory> createCategory(
            @RequestBody JobCategory category) {

        return ResponseEntity.ok(adminService.createCategory(category));
    }

    // Get all categories
    @GetMapping("/category")
    public ResponseEntity<List<JobCategory>> getAllCategories() {
        return ResponseEntity.ok(adminService.getAllCategories());
    }

    // Update category
    @PutMapping("/category/{id}")
    public ResponseEntity<JobCategory> updateCategory(
            @PathVariable Long id,
            @RequestBody JobCategory category) {

        return ResponseEntity.ok(adminService.updateCategory(id, category));
    }

    // Delete category
    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.deleteCategory(id));
    }
}

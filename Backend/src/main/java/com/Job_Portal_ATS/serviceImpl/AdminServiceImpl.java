package com.Job_Portal_ATS.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Job_Portal_ATS.dto.RecruiterApprovalDTO;
import com.Job_Portal_ATS.dto.UserDTO;
import com.Job_Portal_ATS.entity.ApprovalStatus;
import com.Job_Portal_ATS.entity.JobCategory;
import com.Job_Portal_ATS.entity.RecruiterProfile;
import com.Job_Portal_ATS.entity.Role;
import com.Job_Portal_ATS.entity.User;
import com.Job_Portal_ATS.repository.JobCategoryRepository;
import com.Job_Portal_ATS.repository.RecruiterProfileRepository;
import com.Job_Portal_ATS.repository.UserRepository;
import com.Job_Portal_ATS.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecruiterProfileRepository recruiterRepository;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    // ================= USERS =================

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream().map(user -> {

            UserDTO dto = new UserDTO();
            dto.setUserId(user.getId());
            dto.setFullName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole().toString());
            dto.setAccountStatus(user.getStatus());

            return dto;

        }).collect(Collectors.toList());
    }

    // ================= RECRUITER APPROVAL =================

    @Override
    public String updateRecruiterStatus(RecruiterApprovalDTO dto) {

        User user = userRepository.findById(dto.getRecruiterId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.getRole() != Role.RECRUITER) {
            throw new RuntimeException("User is not a recruiter");
        }

        user.setApprovalStatus(dto.getStatus());
        userRepository.save(user);

        return "Recruiter status updated successfully";
    }


    // ================= JOB CATEGORY =================

    @Override
    public JobCategory createCategory(JobCategory category) {
        return jobCategoryRepository.save(category);
    }

    @Override
    public List<JobCategory> getAllCategories() {
        return jobCategoryRepository.findAll();
    }

    @Override
    public JobCategory updateCategory(Long id, JobCategory category) {

        Optional<JobCategory> optional =
                jobCategoryRepository.findById(id);

        if (optional.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        JobCategory existing = optional.get();

        // ✔ FIX HERE
        existing.setCategoryName(category.getCategoryName());
        existing.setDescription(category.getDescription());

        return jobCategoryRepository.save(existing);
    }

    @Override
    public String deleteCategory(Long id) {
        jobCategoryRepository.deleteById(id);
        return "Category deleted successfully";
    }
}

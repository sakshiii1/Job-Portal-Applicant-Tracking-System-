package com.Job_Portal_ATS.service;

import java.util.List;

import com.Job_Portal_ATS.dto.RecruiterApprovalDTO;
import com.Job_Portal_ATS.dto.UserDTO;
import com.Job_Portal_ATS.entity.JobCategory;

public interface AdminService {


// View all users
List<UserDTO> getAllUsers();

// Approve / Reject recruiter
String updateRecruiterStatus(RecruiterApprovalDTO dto);

// Manage Job Categories
JobCategory createCategory(JobCategory category);

List<JobCategory> getAllCategories();

JobCategory updateCategory(Long id, JobCategory category);

String deleteCategory(Long id);


}

package com.Job_Portal_ATS.service;

import java.util.List;

import com.Job_Portal_ATS.dto.LoginRequestDTO;
import com.Job_Portal_ATS.dto.RegisterRequestDTO;
import com.Job_Portal_ATS.entity.User;

public interface AuthService {

    String login(LoginRequestDTO dto);
    // Register already hoga
    String registerUser(RegisterRequestDTO dto);

    // 🔽 ADD THIS
    List<User> getAllUsers();
}


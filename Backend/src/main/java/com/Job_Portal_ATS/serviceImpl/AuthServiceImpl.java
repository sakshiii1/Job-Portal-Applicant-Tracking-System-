package com.Job_Portal_ATS.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.Job_Portal_ATS.config.JwtUtil;
import com.Job_Portal_ATS.dto.LoginRequestDTO;
import com.Job_Portal_ATS.dto.RegisterRequestDTO;
import com.Job_Portal_ATS.entity.ApprovalStatus;
import com.Job_Portal_ATS.entity.Role;
import com.Job_Portal_ATS.entity.User;
import com.Job_Portal_ATS.repository.UserRepository;
import com.Job_Portal_ATS.service.AuthService;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(LoginRequestDTO dto) {

        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        // 🚨 Approval Check
        if (user.getRole() == Role.RECRUITER &&
        	    user.getApprovalStatus() != ApprovalStatus.APPROVED) {

        	    throw new ResponseStatusException(
        	            HttpStatus.FORBIDDEN,
        	            "Recruiter not approved by admin yet."
        	    );
        	}


        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }


    @Override
    public String registerUser(RegisterRequestDTO dto) {

        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }

        User user = new User();
        user.setName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.valueOf(dto.getRole()));
        user.setStatus("ACTIVE");

        if (user.getRole() == Role.RECRUITER) {
            user.setApprovalStatus(ApprovalStatus.PENDING);
        } else {
            user.setApprovalStatus(ApprovalStatus.APPROVED);
        }

        userRepo.save(user);

        return "User registered successfully!";
    }


    @Override
    public List<User> getAllUsers() {  // ✅ AB IMPLEMENT KAR DIYA
        return userRepo.findAll();  // Saare users return karo
    }
}
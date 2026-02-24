package com.Job_Portal_ATS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Job_Portal_ATS.dto.LoginRequestDTO;
import com.Job_Portal_ATS.dto.RegisterRequestDTO;
import com.Job_Portal_ATS.entity.User;
import com.Job_Portal_ATS.service.AuthService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")

@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequestDTO dto) {

        return ResponseEntity.ok(
                authService.registerUser(dto)
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDTO dto) {

        String token = authService.login(dto);
        return ResponseEntity.ok(token);
    }

    // ✅ GET ALL REGISTERED USERS
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok(
                authService.getAllUsers()
        );
    }
}

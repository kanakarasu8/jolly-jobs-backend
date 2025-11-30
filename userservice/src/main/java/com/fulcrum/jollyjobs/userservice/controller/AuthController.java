package com.fulcrum.jollyjobs.userservice.controller;

import com.fulcrum.jollyjobs.userservice.controller.response.AuthResponse;
import com.fulcrum.jollyjobs.userservice.data.User;
import com.fulcrum.jollyjobs.userservice.dto.LoginRequest;
import com.fulcrum.jollyjobs.userservice.dto.RegisterRequest;
import com.fulcrum.jollyjobs.userservice.repository.UserRepository;
import com.fulcrum.jollyjobs.userservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")

public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder encoder;

    //private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {

        // Check if user already exists
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        // Convert DTO → Entity
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .userType(request.getUserType())
                .skills(request.getSkills())
                .experience(request.getExperience())
                .state(request.getState())
                .district(request.getDistrict())
                .location(request.getLocation())
                .phone(request.getPhone())
                .build();

        // Save user in DB
        User savedUser = userRepo.save(user);

        // Generate JWT Token
        String token = jwtUtil.generateToken(savedUser.getEmail());

        return new AuthResponse(token, savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Find user by email
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Check password
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse(token, user));
    }



}

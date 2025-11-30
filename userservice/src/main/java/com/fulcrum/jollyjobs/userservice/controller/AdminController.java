package com.fulcrum.jollyjobs.userservice.controller;

import com.fulcrum.jollyjobs.userservice.data.User;
import com.fulcrum.jollyjobs.userservice.dto.AdminRequest;
import com.fulcrum.jollyjobs.userservice.repository.JobPostRepository;
import com.fulcrum.jollyjobs.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    UserRepository userRepo;

    @Autowired
    JobPostRepository jobPostRepo;
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam String adminEmail) {
        // Case-insensitive email search
        User admin = userRepo.findByEmailIgnoreCase(adminEmail);

        if (admin == null || !"admin".equalsIgnoreCase(admin.getUserType())) {
            return ResponseEntity.status(403).body("Access denied. Only admin can view all users.");
        }

        return ResponseEntity.ok(userRepo.findAll());
    }

    // ✅ Get all job posts — admin only
    @GetMapping("/jobs")
    public ResponseEntity<?> getAllJobPosts(@RequestParam String adminEmail) {
        User admin = userRepo.findByEmail(adminEmail);

        if (admin == null || !"admin".equalsIgnoreCase(admin.getUserType())) {
            return ResponseEntity.status(403).body("Access denied. Only admin can view all job posts.");
        }

        return ResponseEntity.ok(jobPostRepo.findAll());
    }
}

package com.fulcrum.jollyjobs.userservice.controller;

import com.fulcrum.jollyjobs.userservice.data.JobPost;
import com.fulcrum.jollyjobs.userservice.data.User;
import com.fulcrum.jollyjobs.userservice.repository.JobPostRepository;
import com.fulcrum.jollyjobs.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
@CrossOrigin(origins = "*")
public class JobController {
@Autowired
UserRepository userRepo;
@Autowired
JobPostRepository jobPostRepo;
    @PostMapping("/post")
    public ResponseEntity<?> createJobPost(@RequestBody JobPost jobPost, @RequestParam String email) {
        User user = userRepo.findByEmail(email);
        if (user == null || !user.getUserType().equalsIgnoreCase("employer")) {
            return ResponseEntity.status(403).body("Only employers can post jobs");
        }

        // Instead of jobPost.setUser(user);
        jobPost.setUserId(user.getId());

        JobPost savedJob = jobPostRepo.save(jobPost);

        return ResponseEntity.ok(savedJob);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllJobs() {
        // Return all jobs, no admin check
        return ResponseEntity.ok(jobPostRepo.findAll());
    }
}

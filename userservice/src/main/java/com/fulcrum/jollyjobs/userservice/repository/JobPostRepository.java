package com.fulcrum.jollyjobs.userservice.repository;

import com.fulcrum.jollyjobs.userservice.data.JobPost;
import com.fulcrum.jollyjobs.userservice.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

}

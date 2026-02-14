package com.jobportal.JobApplicationTracker.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.booking.app.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findByEmail(String email);

	com.jobportal.JobApplicationTracker.entity.User save(com.jobportal.JobApplicationTracker.entity.User user);
}

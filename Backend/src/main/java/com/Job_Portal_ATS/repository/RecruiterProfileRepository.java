package com.Job_Portal_ATS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Job_Portal_ATS.entity.RecruiterProfile;
import com.Job_Portal_ATS.entity.User;

public interface RecruiterProfileRepository
        extends JpaRepository<RecruiterProfile, Long> {

	Optional<RecruiterProfile> findByUser(User user);


}

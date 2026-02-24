package com.Job_Portal_ATS.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Job_Portal_ATS.entity.CandidateProfile;
import com.Job_Portal_ATS.entity.User;


@Repository
public interface CandidateProfileRepository
extends JpaRepository<CandidateProfile, Long> {


Optional<CandidateProfile> findByUser(User user);


}

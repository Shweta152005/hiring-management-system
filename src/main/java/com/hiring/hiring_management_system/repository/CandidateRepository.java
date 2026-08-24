package com.hiring.hiring_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hiring.hiring_management_system.entity.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

}
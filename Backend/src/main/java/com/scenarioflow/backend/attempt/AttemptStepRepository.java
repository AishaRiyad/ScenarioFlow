package com.scenarioflow.backend.attempt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptStepRepository extends JpaRepository<AttemptStep, Long> {
    List<AttemptStep> findByAttemptIdOrderByStepOrderAsc(Long attemptId);
}
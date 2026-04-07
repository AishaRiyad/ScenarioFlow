package com.scenarioflow.backend.choice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findByNodeId(Long nodeId);
}
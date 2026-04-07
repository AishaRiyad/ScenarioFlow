package com.scenarioflow.backend.node;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioNodeRepository extends JpaRepository<ScenarioNode, Long> {
    List<ScenarioNode> findByScenarioId(Long scenarioId);
}
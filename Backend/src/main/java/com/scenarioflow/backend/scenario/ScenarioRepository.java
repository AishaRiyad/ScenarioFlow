package com.scenarioflow.backend.scenario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {

    List<Scenario> findByStatus(ScenarioStatus status);

    List<Scenario> findByStatusAndCategoryIgnoreCase(
            ScenarioStatus status,
            String category
    );

    List<Scenario> findByStatusAndTitleContainingIgnoreCase(
            ScenarioStatus status,
            String title
    );
}
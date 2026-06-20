package com.scenarioflow.backend.comment;

import com.scenarioflow.backend.scenario.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScenarioCommentRepository extends JpaRepository<ScenarioComment, Long> {
    List<ScenarioComment> findByScenarioOrderByCreatedAtDesc(Scenario scenario);
}
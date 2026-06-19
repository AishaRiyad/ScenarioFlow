package com.scenarioflow.backend.rating;

import com.scenarioflow.backend.scenario.Scenario;
import com.scenarioflow.backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScenarioRatingRepository extends JpaRepository<ScenarioRating, Long> {
    List<ScenarioRating> findByScenario(Scenario scenario);
    Optional<ScenarioRating> findByScenarioAndUser(Scenario scenario, User user);
}
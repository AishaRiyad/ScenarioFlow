package com.scenarioflow.backend.dashboard;

import com.scenarioflow.backend.attempt.AttemptRepository;
import com.scenarioflow.backend.scenario.ScenarioRepository;
import com.scenarioflow.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final ScenarioRepository scenarioRepository;
    private final AttemptRepository attemptRepository;

    public DashboardStatsResponse getStats() {
        var attempts = attemptRepository.findAll();

        long completed = attempts.stream()
                .filter(a -> Boolean.TRUE.equals(a.getCompleted()))
                .count();

        double averageScore = attempts.stream()
                .filter(a -> a.getFinalScore() != null)
                .mapToInt(a -> a.getFinalScore())
                .average()
                .orElse(0);

        return DashboardStatsResponse.builder()
                .totalUsers(userRepository.count())
                .totalScenarios(scenarioRepository.count())
                .totalAttempts(attemptRepository.count())
                .completedAttempts(completed)
                .averageScore(averageScore)
                .build();
    }
}
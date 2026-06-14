package com.scenarioflow.backend.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardStatsResponse {
    private long totalUsers;
    private long totalScenarios;
    private long totalAttempts;
    private long completedAttempts;
    private double averageScore;
}
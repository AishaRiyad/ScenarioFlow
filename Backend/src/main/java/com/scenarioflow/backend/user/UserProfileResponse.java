package com.scenarioflow.backend.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private String fullName;
    private String email;
    private String role;
    private long totalAttempts;
    private long completedAttempts;
    private int bestScore;
    private double averageScore;
}
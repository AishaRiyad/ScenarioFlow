package com.scenarioflow.backend.attempt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeaderboardEntryResponse {

    private String fullName;
    private Integer bestScore;
    private Long completedAttempts;
}
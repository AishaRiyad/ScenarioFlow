package com.scenarioflow.backend.attempt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttemptSummaryResponse {
    private Long attemptId;
    private Long scenarioId;
    private String scenarioTitle;
    private int finalScore;
    private boolean completed;
}
package com.scenarioflow.backend.attempt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttemptResultResponse {
    private Long attemptId;
    private String scenarioTitle;
    private int finalScore;
    private boolean completed;
    private String resultText;
}
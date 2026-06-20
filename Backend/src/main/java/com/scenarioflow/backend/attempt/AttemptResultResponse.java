package com.scenarioflow.backend.attempt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttemptResultResponse {

    private Long attemptId;
    private String scenarioTitle;
    private boolean completed;
    private String resultText;
    private int finalScore;
    private String resultMessage;
    private String feedback;
    private String strengths;
    private String improvementAreas;
    private Integer decisionsCount;
}
package com.scenarioflow.backend.attempt;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SubmitChoiceRequest {
    @NotNull
    private Long attemptId;

    @NotNull
    private Long choiceId;
}
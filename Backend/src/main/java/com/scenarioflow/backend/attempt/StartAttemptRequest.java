package com.scenarioflow.backend.attempt;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StartAttemptRequest {
    @NotNull
    private Long scenarioId;
}
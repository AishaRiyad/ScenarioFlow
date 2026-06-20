package com.scenarioflow.backend.node;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateNodePositionRequest {
    @NotNull
    private Double positionX;

    @NotNull
    private Double positionY;
}
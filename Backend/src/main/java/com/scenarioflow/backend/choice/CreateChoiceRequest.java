package com.scenarioflow.backend.choice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateChoiceRequest {

    @NotNull
    private Long nodeId;

    @NotBlank
    private String choiceText;

    @NotNull
    private Long nextNodeId;

    private Integer scoreImpact;
}
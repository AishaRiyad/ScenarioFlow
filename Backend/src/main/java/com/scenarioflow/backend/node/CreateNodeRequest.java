package com.scenarioflow.backend.node;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateNodeRequest {

    @NotNull
    private Long scenarioId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private NodeType nodeType;

    private String feedbackText;

    private Integer scoreValue;
}
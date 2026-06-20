package com.scenarioflow.backend.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequest {

    @NotNull
    private Long scenarioId;

    @NotBlank
    private String content;
}
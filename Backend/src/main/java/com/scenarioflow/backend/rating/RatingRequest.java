package com.scenarioflow.backend.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest {

    @NotNull
    private Long scenarioId;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;
}
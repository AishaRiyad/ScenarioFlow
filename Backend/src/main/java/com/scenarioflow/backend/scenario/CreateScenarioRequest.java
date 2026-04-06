package com.scenarioflow.backend.scenario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateScenarioRequest {

    @NotBlank
    private String title;

    private String description;

    private String category;

    private String difficulty;
}
package com.scenarioflow.backend.scenario;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScenarioValidationResponse {
    private boolean valid;
    private List<String> errors;
}
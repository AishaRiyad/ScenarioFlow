package com.scenarioflow.backend.scenario;

import lombok.Data;

@Data
public class CreateScenarioFromTemplateRequest {

    private ScenarioTemplateType templateType;

    private String customTitle;
}
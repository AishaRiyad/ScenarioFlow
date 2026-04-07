package com.scenarioflow.backend.choice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChoiceResponse {
    private Long id;
    private String choiceText;
    private Long nextNodeId;
    private Integer scoreImpact;
}
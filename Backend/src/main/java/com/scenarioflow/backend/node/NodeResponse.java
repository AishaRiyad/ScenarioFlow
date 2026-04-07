package com.scenarioflow.backend.node;

import com.scenarioflow.backend.choice.ChoiceResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NodeResponse {
    private Long id;
    private String title;
    private String content;
    private NodeType nodeType;
    private String feedbackText;
    private Integer scoreValue;
    private List<ChoiceResponse> choices;
}
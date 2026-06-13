package com.scenarioflow.backend.attempt;

import com.scenarioflow.backend.choice.ChoiceResponse;
import com.scenarioflow.backend.node.NodeType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayNodeResponse {
    private Long attemptId;
    private Long nodeId;
    private String title;
    private String content;
    private NodeType nodeType;
    private String feedbackText;
    private Integer currentScore;
    private List<ChoiceResponse> choices;
}
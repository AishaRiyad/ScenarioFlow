package com.scenarioflow.backend.node;

import com.scenarioflow.backend.scenario.Scenario;
import com.scenarioflow.backend.scenario.ScenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioNodeService {

    private final ScenarioRepository scenarioRepository;
    private final ScenarioNodeRepository scenarioNodeRepository;

    public ScenarioNode createNode(CreateNodeRequest request) {
        Scenario scenario = scenarioRepository.findById(request.getScenarioId())
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        if (request.getNodeType() == NodeType.START) {
            boolean hasStartNode = scenarioNodeRepository.findByScenarioId(request.getScenarioId())
                    .stream()
                    .anyMatch(node -> node.getNodeType() == NodeType.START);

            if (hasStartNode) {
                throw new RuntimeException("Scenario already has a START node");
            }
        }

        ScenarioNode node = ScenarioNode.builder()
                .scenario(scenario)
                .title(request.getTitle())
                .content(request.getContent())
                .nodeType(request.getNodeType())
                .feedbackText(request.getFeedbackText())
                .scoreValue(request.getScoreValue() != null ? request.getScoreValue() : 0)
                .build();

        return scenarioNodeRepository.save(node);
    }

    public List<ScenarioNode> getNodesByScenario(Long scenarioId) {
        return scenarioNodeRepository.findByScenarioId(scenarioId);
    }
}
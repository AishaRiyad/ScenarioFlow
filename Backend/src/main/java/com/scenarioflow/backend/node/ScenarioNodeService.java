package com.scenarioflow.backend.node;

import com.scenarioflow.backend.choice.ChoiceRepository;
import com.scenarioflow.backend.choice.ChoiceResponse;
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
    private final ChoiceRepository choiceRepository;

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

    public List<NodeResponse> getNodesWithChoices(Long scenarioId) {
        List<ScenarioNode> nodes = scenarioNodeRepository.findByScenarioId(scenarioId);

        return nodes.stream().map(node -> {
            List<ChoiceResponse> choices = choiceRepository.findByNodeId(node.getId())
                    .stream()
                    .map(choice -> ChoiceResponse.builder()
                            .id(choice.getId())
                            .choiceText(choice.getChoiceText())
                            .nextNodeId(choice.getNextNode().getId())
                            .scoreImpact(choice.getScoreImpact())
                            .build())
                    .toList();

            return NodeResponse.builder()
                    .id(node.getId())
                    .title(node.getTitle())
                    .content(node.getContent())
                    .nodeType(node.getNodeType())
                    .feedbackText(node.getFeedbackText())
                    .scoreValue(node.getScoreValue())
                    .choices(choices)
                    .build();
        }).toList();
    }
}
package com.scenarioflow.backend.choice;

import com.scenarioflow.backend.node.NodeType;
import com.scenarioflow.backend.node.ScenarioNode;
import com.scenarioflow.backend.node.ScenarioNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChoiceService {

    private final ChoiceRepository choiceRepository;
    private final ScenarioNodeRepository scenarioNodeRepository;

    public Choice createChoice(CreateChoiceRequest request) {
        ScenarioNode currentNode = scenarioNodeRepository.findById(request.getNodeId())
                .orElseThrow(() -> new RuntimeException("Current node not found"));

        ScenarioNode nextNode = scenarioNodeRepository.findById(request.getNextNodeId())
                .orElseThrow(() -> new RuntimeException("Next node not found"));

        if (!currentNode.getScenario().getId().equals(nextNode.getScenario().getId())) {
            throw new RuntimeException("Nodes must belong to the same scenario");
        }

        if (currentNode.getNodeType() == NodeType.END) {
            throw new RuntimeException("Cannot add choices to an END node");
        }

        Choice choice = Choice.builder()
                .node(currentNode)
                .choiceText(request.getChoiceText())
                .nextNode(nextNode)
                .scoreImpact(request.getScoreImpact() != null ? request.getScoreImpact() : 0)
                .build();

        return choiceRepository.save(choice);
    }

    public List<Choice> getChoicesByNode(Long nodeId) {
        return choiceRepository.findByNodeId(nodeId);
    }
}
package com.scenarioflow.backend.node;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nodes")
@RequiredArgsConstructor
public class ScenarioNodeController {

    private final ScenarioNodeService scenarioNodeService;

    @PostMapping
    public ScenarioNode createNode(@RequestBody @Valid CreateNodeRequest request) {
        return scenarioNodeService.createNode(request);
    }

    @GetMapping("/scenario/{scenarioId}")
    public List<NodeResponse> getNodesByScenario(@PathVariable Long scenarioId) {
        return scenarioNodeService.getNodesWithChoices(scenarioId);
    }

    @PatchMapping("/{nodeId}/position")
    public ScenarioNode updatePosition(
            @PathVariable Long nodeId,
            @RequestBody @Valid UpdateNodePositionRequest request
    ) {
        return scenarioNodeService.updatePosition(nodeId, request);
    }
}
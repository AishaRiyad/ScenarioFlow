package com.scenarioflow.backend.scenario;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenarios")
@RequiredArgsConstructor
public class ScenarioController {

    private final ScenarioService scenarioService;

    @PostMapping
    public Scenario createScenario(
            @RequestBody @Valid CreateScenarioRequest request,
            Authentication authentication
    ) {
        return scenarioService.createScenario(
                request,
                authentication.getName()
        );
    }

    @GetMapping
    public List<Scenario> getAllScenarios() {
        return scenarioService.getAllScenarios();
    }

    @GetMapping("/{id}")
    public Scenario getScenarioById(@PathVariable Long id) {
        return scenarioService.getScenarioById(id);
    }

    @GetMapping("/published")
    public List<Scenario> getPublishedScenarios(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword
    ) {
        return scenarioService.searchPublishedScenarios(
                category,
                keyword
        );
    }

    @PatchMapping("/{scenarioId}/publish")
    public Scenario publishScenario(@PathVariable Long scenarioId) {
        return scenarioService.publishScenario(scenarioId);
    }
}
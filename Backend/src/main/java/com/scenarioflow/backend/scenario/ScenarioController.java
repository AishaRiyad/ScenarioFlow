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
        return scenarioService.createScenario(request, authentication.getName());
    }

    @GetMapping
    public List<Scenario> getAllScenarios() {
        return scenarioService.getAllScenarios();
    }

    @GetMapping("/published")
    public List<Scenario> getPublishedScenarios() {
        return scenarioService.getPublishedScenarios();
    }
}
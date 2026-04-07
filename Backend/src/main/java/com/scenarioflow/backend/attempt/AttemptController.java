package com.scenarioflow.backend.attempt;

import com.scenarioflow.backend.node.ScenarioNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attempts")
@RequiredArgsConstructor
public class AttemptController {

    private final AttemptService attemptService;

    @PostMapping("/start")
    public ScenarioNode start(@RequestBody @Valid StartAttemptRequest request,
                              Authentication auth) {
        return attemptService.startAttempt(request.getScenarioId(), auth.getName());
    }

    @PostMapping("/choice")
    public ScenarioNode choose(@RequestBody @Valid SubmitChoiceRequest request) {
        return attemptService.submitChoice(request.getAttemptId(), request.getChoiceId());
    }
}
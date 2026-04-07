package com.scenarioflow.backend.choice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/choices")
@RequiredArgsConstructor
public class ChoiceController {

    private final ChoiceService choiceService;

    @PostMapping
    public Choice createChoice(@RequestBody @Valid CreateChoiceRequest request) {
        return choiceService.createChoice(request);
    }

    @GetMapping("/node/{nodeId}")
    public List<Choice> getChoicesByNode(@PathVariable Long nodeId) {
        return choiceService.getChoicesByNode(nodeId);
    }
}
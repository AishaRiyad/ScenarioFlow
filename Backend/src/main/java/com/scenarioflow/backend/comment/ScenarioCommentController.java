package com.scenarioflow.backend.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class ScenarioCommentController {

    private final ScenarioCommentService commentService;

    @PostMapping
    public CommentResponse addComment(
            @RequestBody @Valid CommentRequest request,
            Authentication auth
    ) {
        return commentService.addComment(request, auth.getName());
    }

    @GetMapping("/scenario/{scenarioId}")
    public List<CommentResponse> getScenarioComments(@PathVariable Long scenarioId) {
        return commentService.getScenarioComments(scenarioId);
    }
}
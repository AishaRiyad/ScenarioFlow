package com.scenarioflow.backend.comment;

import com.scenarioflow.backend.scenario.Scenario;
import com.scenarioflow.backend.scenario.ScenarioRepository;
import com.scenarioflow.backend.user.User;
import com.scenarioflow.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioCommentService {

    private final ScenarioCommentRepository commentRepository;
    private final ScenarioRepository scenarioRepository;
    private final UserRepository userRepository;

    public CommentResponse addComment(CommentRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Scenario scenario = scenarioRepository.findById(request.getScenarioId())
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        ScenarioComment comment = ScenarioComment.builder()
                .scenario(scenario)
                .user(user)
                .content(request.getContent())
                .build();

        commentRepository.save(comment);

        return mapToResponse(comment);
    }

    public List<CommentResponse> getScenarioComments(Long scenarioId) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        return commentRepository.findByScenarioOrderByCreatedAtDesc(scenario)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private CommentResponse mapToResponse(ScenarioComment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userFullName(comment.getUser().getFullName())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
package com.scenarioflow.backend.attempt;

import com.scenarioflow.backend.choice.Choice;
import com.scenarioflow.backend.choice.ChoiceRepository;
import com.scenarioflow.backend.choice.ChoiceResponse;
import com.scenarioflow.backend.node.NodeType;
import com.scenarioflow.backend.node.ScenarioNode;
import com.scenarioflow.backend.node.ScenarioNodeRepository;
import com.scenarioflow.backend.scenario.Scenario;
import com.scenarioflow.backend.scenario.ScenarioRepository;
import com.scenarioflow.backend.user.User;
import com.scenarioflow.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final AttemptStepRepository stepRepository;
    private final ScenarioRepository scenarioRepository;
    private final ScenarioNodeRepository nodeRepository;
    private final ChoiceRepository choiceRepository;
    private final UserRepository userRepository;

    public PlayNodeResponse startAttempt(Long scenarioId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        ScenarioNode startNode = nodeRepository.findByScenarioId(scenarioId)
                .stream()
                .filter(node -> node.getNodeType() == NodeType.START)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No START node found"));

        Attempt attempt = Attempt.builder()
                .user(user)
                .scenario(scenario)
                .build();

        attemptRepository.save(attempt);

        return buildPlayNodeResponse(attempt, startNode);
    }

    public PlayNodeResponse submitChoice(Long attemptId, Long choiceId) {
        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        Choice choice = choiceRepository.findById(choiceId)
                .orElseThrow(() -> new RuntimeException("Choice not found"));

        ScenarioNode nextNode = choice.getNextNode();

        int stepOrder = stepRepository.findByAttemptIdOrderByStepOrderAsc(attemptId)
                .size() + 1;

        int currentScore = attempt.getFinalScore() + choice.getScoreImpact();

        AttemptStep step = AttemptStep.builder()
                .attempt(attempt)
                .node(choice.getNode())
                .choice(choice)
                .stepOrder(stepOrder)
                .scoreAfterStep(currentScore)
                .build();

        stepRepository.save(step);

        attempt.setFinalScore(currentScore);

        if (nextNode.getNodeType() == NodeType.END) {
            attempt.setCompleted(true);
            attempt.setEndedAt(LocalDateTime.now());
        }

        attemptRepository.save(attempt);

        return buildPlayNodeResponse(attempt, nextNode);
    }

    public List<AttemptSummaryResponse> getMyAttempts(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return attemptRepository.findAll()
                .stream()
                .filter(attempt -> attempt.getUser().getId().equals(user.getId()))
                .map(attempt -> AttemptSummaryResponse.builder()
                        .attemptId(attempt.getId())
                        .scenarioId(attempt.getScenario().getId())
                        .scenarioTitle(attempt.getScenario().getTitle())
                        .finalScore(attempt.getFinalScore())
                        .completed(attempt.getCompleted())
                        .build())
                .toList();
    }

    public AttemptResultResponse getResult(Long attemptId) {

        Attempt attempt = attemptRepository.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        if (!attempt.getCompleted()) {
            throw new RuntimeException("Scenario not completed yet");
        }

        var steps = stepRepository.findByAttemptIdOrderByStepOrderAsc(attemptId);

        int score = attempt.getFinalScore();
        int decisionsCount = steps.size();

        long positiveChoices = steps.stream()
                .filter(step -> step.getChoice().getScoreImpact() != null)
                .filter(step -> step.getChoice().getScoreImpact() > 0)
                .count();

        long weakChoices = steps.stream()
                .filter(step -> step.getChoice().getScoreImpact() != null)
                .filter(step -> step.getChoice().getScoreImpact() <= 0)
                .count();

        String resultMessage;
        String feedback;
        String strengths;
        String improvementAreas;

        if (score >= 25) {
            resultMessage = "Excellent performance";
            feedback = "You showed strong decision-making and handled the scenario with confidence.";
        } else if (score >= 15) {
            resultMessage = "Good performance";
            feedback = "You made mostly effective decisions, with a few areas that can be improved.";
        } else {
            resultMessage = "Needs improvement";
            feedback = "Your choices show that you may need to slow down and analyze the situation more carefully.";
        }

        if (positiveChoices >= weakChoices) {
            strengths = "You demonstrated good judgment and selected several constructive options.";
        } else {
            strengths = "You completed the scenario and engaged with the decision-making process.";
        }

        if (weakChoices == 0) {
            improvementAreas = "Keep practicing with more complex scenarios to strengthen your consistency.";
        } else if (weakChoices <= 2) {
            improvementAreas = "Try to review the consequences of each option before making a decision.";
        } else {
            improvementAreas = "Focus on understanding the context and comparing options more carefully.";
        }

        return AttemptResultResponse.builder()
                .attemptId(attempt.getId())
                .finalScore(score)
                .resultMessage(resultMessage)
                .feedback(feedback)
                .strengths(strengths)
                .improvementAreas(improvementAreas)
                .decisionsCount(decisionsCount)
                .build();
    }

    private PlayNodeResponse buildPlayNodeResponse(Attempt attempt, ScenarioNode node) {
        List<ChoiceResponse> choices = choiceRepository.findByNodeId(node.getId())
                .stream()
                .map(choice -> ChoiceResponse.builder()
                        .id(choice.getId())
                        .choiceText(choice.getChoiceText())
                        .nextNodeId(choice.getNextNode().getId())
                        .scoreImpact(choice.getScoreImpact())
                        .build())
                .toList();

        return PlayNodeResponse.builder()
                .attemptId(attempt.getId())
                .nodeId(node.getId())
                .title(node.getTitle())
                .content(node.getContent())
                .nodeType(node.getNodeType())
                .feedbackText(node.getFeedbackText())
                .currentScore(attempt.getFinalScore())
                .choices(choices)
                .build();
    }
}
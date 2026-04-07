package com.scenarioflow.backend.attempt;

import com.scenarioflow.backend.choice.Choice;
import com.scenarioflow.backend.choice.ChoiceRepository;
import com.scenarioflow.backend.node.NodeType;
import com.scenarioflow.backend.node.ScenarioNode;
import com.scenarioflow.backend.node.ScenarioNodeRepository;
import com.scenarioflow.backend.scenario.Scenario;
import com.scenarioflow.backend.scenario.ScenarioRepository;
import com.scenarioflow.backend.user.User;
import com.scenarioflow.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final AttemptStepRepository stepRepository;
    private final ScenarioRepository scenarioRepository;
    private final ScenarioNodeRepository nodeRepository;
    private final ChoiceRepository choiceRepository;
    private final UserRepository userRepository;

    public ScenarioNode startAttempt(Long scenarioId, String email) {

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

        return startNode;
    }

    public ScenarioNode submitChoice(Long attemptId, Long choiceId) {

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

        return nextNode;
    }
}
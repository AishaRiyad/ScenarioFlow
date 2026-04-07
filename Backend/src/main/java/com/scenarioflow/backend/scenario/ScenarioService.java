package com.scenarioflow.backend.scenario;

import com.scenarioflow.backend.user.User;
import com.scenarioflow.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final UserRepository userRepository;

    public Scenario createScenario(CreateScenarioRequest request, String currentUserEmail) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Scenario scenario = Scenario.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .difficulty(request.getDifficulty())
                .status(ScenarioStatus.DRAFT)
                .createdBy(user)
                .build();

        return scenarioRepository.save(scenario);
    }

    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    public List<Scenario> getPublishedScenarios() {
        return scenarioRepository.findByStatus(ScenarioStatus.PUBLISHED);
    }
}
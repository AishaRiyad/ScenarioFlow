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

    public Scenario createFromTemplate(
            CreateScenarioFromTemplateRequest request,
            String currentUserEmail
    ) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Scenario scenario = new Scenario();

        scenario.setCreatedBy(user);
        scenario.setStatus(ScenarioStatus.DRAFT);

        switch (request.getTemplateType()) {
            case JOB_INTERVIEW -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Job Interview Simulation");
                scenario.setDescription("Practice handling interview questions and decisions.");
                scenario.setCategory("Career");
                scenario.setDifficulty("Medium");
            }
            case CUSTOMER_SERVICE -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Customer Service Challenge");
                scenario.setDescription("Handle customer interactions and complaints.");
                scenario.setCategory("Communication");
                scenario.setDifficulty("Easy");
            }
            case LEADERSHIP -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Leadership Decision Making");
                scenario.setDescription("Lead a team through difficult situations.");
                scenario.setCategory("Leadership");
                scenario.setDifficulty("Hard");
            }
            case ETHICS -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Ethical Decision Making");
                scenario.setDescription("Analyze ethical dilemmas and choose actions.");
                scenario.setCategory("Ethics");
                scenario.setDifficulty("Medium");
            }
            case CONFLICT_RESOLUTION -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Conflict Resolution Workshop");
                scenario.setDescription("Resolve workplace and team conflicts.");
                scenario.setCategory("Communication");
                scenario.setDifficulty("Medium");
            }
        }

        return scenarioRepository.save(scenario);
    }

    public Scenario publishScenario(Long scenarioId) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        scenario.setStatus(ScenarioStatus.PUBLISHED);

        return scenarioRepository.save(scenario);
    }

    public void deleteScenario(Long scenarioId) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        scenarioRepository.delete(scenario);
    }

    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    public List<Scenario> getPublishedScenarios() {
        return scenarioRepository.findByStatus(ScenarioStatus.PUBLISHED);
    }

    public Scenario getScenarioById(Long id) {
        return scenarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));
    }

    public List<Scenario> getRecommendations(Long scenarioId) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        return scenarioRepository.findByStatusAndCategoryIgnoreCase(
                        ScenarioStatus.PUBLISHED,
                        scenario.getCategory()
                ).stream()
                .filter(s -> !s.getId().equals(scenarioId))
                .limit(3)
                .toList();
    }

    public List<Scenario> searchPublishedScenarios(
            String category,
            String keyword
    ) {
        if (category != null && !category.isBlank()) {
            return scenarioRepository.findByStatusAndCategoryIgnoreCase(
                    ScenarioStatus.PUBLISHED,
                    category
            );
        }

        if (keyword != null && !keyword.isBlank()) {
            return scenarioRepository.findByStatusAndTitleContainingIgnoreCase(
                    ScenarioStatus.PUBLISHED,
                    keyword
            );
        }

        return scenarioRepository.findByStatus(ScenarioStatus.PUBLISHED);
    }
}
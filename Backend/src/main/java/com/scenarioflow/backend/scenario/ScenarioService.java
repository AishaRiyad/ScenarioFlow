package com.scenarioflow.backend.scenario;

import com.scenarioflow.backend.attempt.Attempt;
import com.scenarioflow.backend.attempt.AttemptRepository;
import com.scenarioflow.backend.attempt.AttemptStepRepository;
import com.scenarioflow.backend.choice.Choice;
import com.scenarioflow.backend.choice.ChoiceRepository;
import com.scenarioflow.backend.node.NodeType;
import com.scenarioflow.backend.node.ScenarioNode;
import com.scenarioflow.backend.node.ScenarioNodeRepository;
import com.scenarioflow.backend.user.User;
import com.scenarioflow.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final UserRepository userRepository;
    private final ScenarioNodeRepository scenarioNodeRepository;
    private final ChoiceRepository choiceRepository;
    private final AttemptRepository attemptRepository;
    private final AttemptStepRepository attemptStepRepository;

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

    @Transactional
    public Scenario createFromTemplate(
            CreateScenarioFromTemplateRequest request,
            String currentUserEmail
    ) {
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Scenario scenario = new Scenario();
        scenario.setCreatedBy(user);
        scenario.setStatus(ScenarioStatus.DRAFT);

        String startTitle;
        String startContent;
        String decisionTitle;
        String decisionContent;
        String endTitle;
        String endContent;
        String choiceOne;
        String choiceTwo;

        switch (request.getTemplateType()) {
            case JOB_INTERVIEW -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Job Interview Simulation");
                scenario.setDescription("Practice handling interview questions and professional decisions.");
                scenario.setCategory("Career");
                scenario.setDifficulty("Medium");
                startTitle = "Interview Begins";
                startContent = "You enter the interview room and meet the hiring manager.";
                decisionTitle = "First Question";
                decisionContent = "The interviewer asks you to introduce yourself.";
                endTitle = "Interview Completed";
                endContent = "The interview ends and your performance is evaluated.";
                choiceOne = "Greet confidently and sit calmly";
                choiceTwo = "Answer with clear examples";
            }
            case CUSTOMER_SERVICE -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Customer Service Challenge");
                scenario.setDescription("Handle customer interactions and solve complaints professionally.");
                scenario.setCategory("Communication");
                scenario.setDifficulty("Easy");
                startTitle = "Customer Complaint";
                startContent = "A customer contacts you angrily about a delayed order.";
                decisionTitle = "Respond to the Customer";
                decisionContent = "You need to choose how to respond to calm the situation.";
                endTitle = "Issue Resolved";
                endContent = "The customer feels heard and the issue is handled.";
                choiceOne = "Listen carefully and apologize";
                choiceTwo = "Offer a clear solution";
            }
            case LEADERSHIP -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Leadership Decision Making");
                scenario.setDescription("Lead a team through a difficult decision.");
                scenario.setCategory("Leadership");
                scenario.setDifficulty("Hard");
                startTitle = "Team Problem";
                startContent = "Your team is struggling to finish an important task on time.";
                decisionTitle = "Choose Your Leadership Style";
                decisionContent = "You must decide how to guide the team.";
                endTitle = "Team Stabilized";
                endContent = "The team regains focus and continues working.";
                choiceOne = "Ask the team about blockers";
                choiceTwo = "Create a clear action plan";
            }
            case ETHICS -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Ethical Decision Making");
                scenario.setDescription("Analyze ethical dilemmas and choose responsible actions.");
                scenario.setCategory("Ethics");
                scenario.setDifficulty("Medium");
                startTitle = "Ethical Dilemma";
                startContent = "You notice a teammate taking credit for someone else's work.";
                decisionTitle = "Choose Your Response";
                decisionContent = "You need to decide how to handle the situation fairly.";
                endTitle = "Ethical Action Taken";
                endContent = "You respond in a way that supports fairness and respect.";
                choiceOne = "Speak privately with the teammate";
                choiceTwo = "Encourage giving proper credit";
            }
            case CONFLICT_RESOLUTION -> {
                scenario.setTitle(request.getCustomTitle() != null ? request.getCustomTitle() : "Conflict Resolution Workshop");
                scenario.setDescription("Resolve workplace and team conflicts constructively.");
                scenario.setCategory("Communication");
                scenario.setDifficulty("Medium");
                startTitle = "Conflict Starts";
                startContent = "Two team members strongly disagree during a meeting.";
                decisionTitle = "Manage the Conflict";
                decisionContent = "You need to choose how to reduce tension.";
                endTitle = "Conflict Resolved";
                endContent = "The team members understand each other better.";
                choiceOne = "Let each person explain calmly";
                choiceTwo = "Find a shared goal";
            }
            default -> throw new RuntimeException("Unsupported template type");
        }

        Scenario savedScenario = scenarioRepository.saveAndFlush(scenario);

        ScenarioNode startNode = scenarioNodeRepository.save(
                ScenarioNode.builder()
                        .scenario(savedScenario)
                        .title(startTitle)
                        .content(startContent)
                        .nodeType(NodeType.START)
                        .feedbackText("Scenario started.")
                        .scoreValue(0)
                        .positionX(120.0)
                        .positionY(120.0)
                        .build()
        );

        ScenarioNode decisionNode = scenarioNodeRepository.save(
                ScenarioNode.builder()
                        .scenario(savedScenario)
                        .title(decisionTitle)
                        .content(decisionContent)
                        .nodeType(NodeType.DECISION)
                        .feedbackText("Consider your options carefully.")
                        .scoreValue(5)
                        .positionX(420.0)
                        .positionY(120.0)
                        .build()
        );

        ScenarioNode endNode = scenarioNodeRepository.save(
                ScenarioNode.builder()
                        .scenario(savedScenario)
                        .title(endTitle)
                        .content(endContent)
                        .nodeType(NodeType.END)
                        .feedbackText("Scenario completed.")
                        .scoreValue(10)
                        .positionX(720.0)
                        .positionY(120.0)
                        .build()
        );

        choiceRepository.save(
                Choice.builder()
                        .node(startNode)
                        .choiceText(choiceOne)
                        .nextNode(decisionNode)
                        .scoreImpact(5)
                        .build()
        );

        choiceRepository.save(
                Choice.builder()
                        .node(decisionNode)
                        .choiceText(choiceTwo)
                        .nextNode(endNode)
                        .scoreImpact(10)
                        .build()
        );

        return savedScenario;
    }

    public ScenarioValidationResponse validateScenario(Long scenarioId) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        var nodes = scenarioNodeRepository.findByScenarioId(scenario.getId());
        List<String> errors = new ArrayList<>();

        long startCount = nodes.stream()
                .filter(node -> node.getNodeType() == NodeType.START)
                .count();

        long endCount = nodes.stream()
                .filter(node -> node.getNodeType() == NodeType.END)
                .count();

        if (startCount == 0) {
            errors.add("Scenario must have one START node.");
        }

        if (startCount > 1) {
            errors.add("Scenario cannot have more than one START node.");
        }

        if (endCount == 0) {
            errors.add("Scenario must have at least one END node.");
        }

        for (ScenarioNode node : nodes) {
            if (node.getNodeType() != NodeType.END) {
                var choices = choiceRepository.findByNodeId(node.getId());

                if (choices.isEmpty()) {
                    errors.add("Node '" + node.getTitle() + "' must have at least one choice.");
                }

                for (var choice : choices) {
                    if (choice.getNextNode() == null) {
                        errors.add("Choice '" + choice.getChoiceText() + "' must point to a next node.");
                    }
                }
            }
        }

        return ScenarioValidationResponse.builder()
                .valid(errors.isEmpty())
                .errors(errors)
                .build();
    }

    public Scenario publishScenario(Long scenarioId) {
        ScenarioValidationResponse validation = validateScenario(scenarioId);

        if (!validation.isValid()) {
            throw new RuntimeException("Scenario is not valid: " + String.join(" | ", validation.getErrors()));
        }

        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        scenario.setStatus(ScenarioStatus.PUBLISHED);

        return scenarioRepository.save(scenario);
    }

    public Scenario unpublishScenario(Long scenarioId) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        scenario.setStatus(ScenarioStatus.DRAFT);
        return scenarioRepository.save(scenario);
    }

    @Transactional
    public void deleteScenario(Long scenarioId) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        List<Attempt> attempts = attemptRepository.findByScenarioId(scenarioId);

        for (Attempt attempt : attempts) {
            attemptStepRepository.deleteAll(
                    attemptStepRepository.findByAttemptIdOrderByStepOrderAsc(attempt.getId())
            );
        }

        attemptRepository.deleteAll(attempts);

        List<ScenarioNode> nodes = scenarioNodeRepository.findByScenarioId(scenarioId);

        for (ScenarioNode node : nodes) {
            List<Choice> choices = choiceRepository.findByNodeId(node.getId());

            for (Choice choice : choices) {
                attemptStepRepository.deleteAll(
                        attemptStepRepository.findByChoiceId(choice.getId())
                );
            }

            choiceRepository.deleteAll(choices);
        }

        scenarioNodeRepository.deleteAll(nodes);
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

    public List<Scenario> searchPublishedScenarios(String category, String keyword) {
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

    public Scenario cloneScenario(Long scenarioId) {
        Scenario original = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        Scenario clonedScenario = Scenario.builder()
                .title(original.getTitle() + " Copy")
                .description(original.getDescription())
                .category(original.getCategory())
                .difficulty(original.getDifficulty())
                .status(ScenarioStatus.DRAFT)
                .createdBy(original.getCreatedBy())
                .build();

        Scenario savedClone = scenarioRepository.save(clonedScenario);

        Map<Long, ScenarioNode> nodeMap = new HashMap<>();

        var originalNodes = scenarioNodeRepository.findByScenarioId(original.getId());

        for (ScenarioNode originalNode : originalNodes) {
            ScenarioNode clonedNode = ScenarioNode.builder()
                    .scenario(savedClone)
                    .title(originalNode.getTitle())
                    .content(originalNode.getContent())
                    .nodeType(originalNode.getNodeType())
                    .feedbackText(originalNode.getFeedbackText())
                    .scoreValue(originalNode.getScoreValue())
                    .positionX(originalNode.getPositionX())
                    .positionY(originalNode.getPositionY())
                    .build();

            ScenarioNode savedNode = scenarioNodeRepository.save(clonedNode);
            nodeMap.put(originalNode.getId(), savedNode);
        }

        var originalChoices = choiceRepository.findByNodeScenarioId(original.getId());

        for (Choice originalChoice : originalChoices) {
            ScenarioNode clonedCurrentNode = nodeMap.get(originalChoice.getNode().getId());
            ScenarioNode clonedNextNode = nodeMap.get(originalChoice.getNextNode().getId());

            if (clonedCurrentNode != null && clonedNextNode != null) {
                Choice clonedChoice = Choice.builder()
                        .node(clonedCurrentNode)
                        .choiceText(originalChoice.getChoiceText())
                        .nextNode(clonedNextNode)
                        .scoreImpact(originalChoice.getScoreImpact())
                        .build();

                choiceRepository.save(clonedChoice);
            }
        }

        return savedClone;
    }
}
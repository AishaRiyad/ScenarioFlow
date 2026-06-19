package com.scenarioflow.backend.rating;

import com.scenarioflow.backend.scenario.Scenario;
import com.scenarioflow.backend.scenario.ScenarioRepository;
import com.scenarioflow.backend.user.User;
import com.scenarioflow.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScenarioRatingService {

    private final ScenarioRatingRepository ratingRepository;
    private final ScenarioRepository scenarioRepository;
    private final UserRepository userRepository;

    public RatingSummaryResponse rateScenario(RatingRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Scenario scenario = scenarioRepository.findById(request.getScenarioId())
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        ScenarioRating rating = ratingRepository.findByScenarioAndUser(scenario, user)
                .orElse(ScenarioRating.builder()
                        .scenario(scenario)
                        .user(user)
                        .build());

        rating.setRating(request.getRating());
        rating.setComment(request.getComment());

        ratingRepository.save(rating);

        return getScenarioRatingSummary(request.getScenarioId());
    }

    public RatingSummaryResponse getScenarioRatingSummary(Long scenarioId) {
        Scenario scenario = scenarioRepository.findById(scenarioId)
                .orElseThrow(() -> new RuntimeException("Scenario not found"));

        var ratings = ratingRepository.findByScenario(scenario);

        double average = ratings.stream()
                .mapToInt(ScenarioRating::getRating)
                .average()
                .orElse(0);

        return RatingSummaryResponse.builder()
                .scenarioId(scenarioId)
                .averageRating(average)
                .totalRatings(ratings.size())
                .build();
    }
}
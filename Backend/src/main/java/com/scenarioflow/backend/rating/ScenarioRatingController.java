package com.scenarioflow.backend.rating;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class ScenarioRatingController {

    private final ScenarioRatingService ratingService;

    @PostMapping
    public RatingSummaryResponse rateScenario(
            @RequestBody @Valid RatingRequest request,
            Authentication auth
    ) {
        return ratingService.rateScenario(request, auth.getName());
    }

    @GetMapping("/scenario/{scenarioId}")
    public RatingSummaryResponse getScenarioRating(@PathVariable Long scenarioId) {
        return ratingService.getScenarioRatingSummary(scenarioId);
    }
}
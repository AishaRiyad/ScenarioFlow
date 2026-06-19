package com.scenarioflow.backend.rating;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingSummaryResponse {
    private Long scenarioId;
    private double averageRating;
    private long totalRatings;
}
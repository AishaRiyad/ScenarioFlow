package com.scenarioflow.backend.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AchievementResponse {
    private String title;
    private String description;
    private String icon;
    private boolean unlocked;
}
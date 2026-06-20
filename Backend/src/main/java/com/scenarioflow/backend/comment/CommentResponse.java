package com.scenarioflow.backend.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private String userFullName;
    private LocalDateTime createdAt;
}
package com.scenarioflow.backend.attempt;

import com.scenarioflow.backend.scenario.Scenario;
import com.scenarioflow.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne(optional = false)
    private Scenario scenario;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private Integer finalScore;

    private Boolean completed;

    @PrePersist
    public void onStart() {
        this.startedAt = LocalDateTime.now();
        this.completed = false;
        this.finalScore = 0;
    }
}
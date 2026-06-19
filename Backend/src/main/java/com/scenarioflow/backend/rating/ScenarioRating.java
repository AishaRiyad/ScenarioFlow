package com.scenarioflow.backend.rating;

import com.scenarioflow.backend.scenario.Scenario;
import com.scenarioflow.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "scenario_ratings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScenarioRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Scenario scenario;

    @ManyToOne(optional = false)
    private User user;

    @Column(nullable = false)
    private Integer rating;

    private String comment;
}
package com.scenarioflow.backend.attempt;

import com.scenarioflow.backend.choice.Choice;
import com.scenarioflow.backend.node.ScenarioNode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attempt_steps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttemptStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Attempt attempt;

    @ManyToOne(optional = false)
    private ScenarioNode node;

    @ManyToOne(optional = false)
    private Choice choice;

    private Integer stepOrder;

    private Integer scoreAfterStep;
}
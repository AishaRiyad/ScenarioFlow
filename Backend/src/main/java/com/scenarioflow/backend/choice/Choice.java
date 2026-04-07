package com.scenarioflow.backend.choice;

import com.scenarioflow.backend.node.ScenarioNode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "choices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "node_id", nullable = false)
    private ScenarioNode node;

    @Column(name = "choice_text", nullable = false)
    private String choiceText;

    @ManyToOne(optional = false)
    @JoinColumn(name = "next_node_id", nullable = false)
    private ScenarioNode nextNode;

    @Column(name = "score_impact")
    private Integer scoreImpact;
}
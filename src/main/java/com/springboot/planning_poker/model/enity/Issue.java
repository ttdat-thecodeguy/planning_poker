package com.springboot.planning_poker.model.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Table(name = "issues") @Data @AllArgsConstructor @NoArgsConstructor
public class Issue {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String link;
    private String description;
    private int storyPoint;
    @ManyToOne
    @JoinColumn(name = "game_table_id")
    private GameTable gameTable;
}

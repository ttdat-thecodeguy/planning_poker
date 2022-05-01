package com.springboot.planning_poker.model.enity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity @Table(name = "issues") @Data @AllArgsConstructor @NoArgsConstructor
public class Issue {

    @Id @GeneratedValue(generator = "generator_id")
    @GenericGenerator(name="generator_id", strategy = "com.springboot.planning_poker.model.enity.generated_id.GeneratorIssueId" )
    private String id;
    private String name;
    private String link;
    private String description;

    @ManyToOne
    @JoinColumn(name = "game_table_id")
    @JsonIgnore
    private GameTable gameTable;

    private String storyPoint;
}

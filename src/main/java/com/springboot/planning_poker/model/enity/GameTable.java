package com.springboot.planning_poker.model.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "game_tables") @Data @AllArgsConstructor @NoArgsConstructor
public class GameTable {
    @Id
    @GeneratedValue(generator = "create_encode_id")
    @GenericGenerator(name = "create_encode_id", strategy = "com.springboot.planning_poker.model.enity.GeneratorTableID")
    private String id;

    private String name;

    private String voting;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User userOwner;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "gameTable")
    private Set<Issue> issues = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tables")
    private Set<User> users = new HashSet<>();
}


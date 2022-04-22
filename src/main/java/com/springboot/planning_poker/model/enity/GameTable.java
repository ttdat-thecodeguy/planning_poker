package com.springboot.planning_poker.model.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity @Table(name = "game_tables") @Data @AllArgsConstructor
public class GameTable {
    @Id
    @GeneratedValue(generator = "create_encode_id")
    @GenericGenerator(name = "create_encode_id", strategy = "com.springboot.planning_poker.model.enity.GeneratorTableID")
    private String id;
    @Column(name = "name", columnDefinition = "varchar(255) default 'Planning poker game'")
    private String name = "Planning poker game";

    @NotBlank(message = "voting isn't empty")
    @NotNull(message = "voting isn't null")
    private String voting;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User userOwner;

    @Column(name = "user_id")
    private Long userOwerId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gameTable")
    private Set<Issue> issues = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {  CascadeType.MERGE })
    private Set<User> joins = new HashSet<>();

    public GameTable(){
        this.name = "Planning poker game";
    }

    public GameTable addUsersJoin(User user){
        joins.add(user);
        return this;
    }
    public GameTable removeUsersJoin(User user){
        joins.remove(user);
        return this;
    }

    public List<Long> getListUserId(){
        return joins.stream().map(item -> item.getId()).collect(Collectors.toList());
    }


}


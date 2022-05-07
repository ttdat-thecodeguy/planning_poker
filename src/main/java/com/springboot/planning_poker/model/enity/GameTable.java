package com.springboot.planning_poker.model.enity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "game_tables") @Data @AllArgsConstructor
public class GameTable {
    @Id
    @GeneratedValue(generator = "create_encode_id")
    @GenericGenerator(name = "create_encode_id", strategy = "com.springboot.planning_poker.model.enity.generated_id.GeneratorTableID")
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

    private Boolean isShowCardByOwner;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gameTable")
    @JsonIgnore
    private Set<Issue> issues = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {  CascadeType.MERGE })
    @JoinTable(name = "game_joins", joinColumns = @JoinColumn(name = "table_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> joins = new HashSet<>();

    @OneToOne(targetEntity = Issue.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "issue_id")
    private Issue issueActive;

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

//    public List<UserSocketRequest> getListUserSocketRequest(){
//        return joins.stream().map(item -> new UserSocketRequest(item.getId(), item.getDisplayName())).collect(Collectors.toList());
//    }


}


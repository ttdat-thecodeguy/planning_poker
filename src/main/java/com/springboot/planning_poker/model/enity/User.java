package com.springboot.planning_poker.model.enity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Data @Table(name = "users") @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "display_name")
    private String displayName;
    private String photo;
    private boolean isSpector;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

//    @ManyToMany(fetch = FetchType.LAZY, cascade = {  CascadeType.MERGE })
//    private Set<GameTable> tables = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Voting> votings = new HashSet<>();

    public User(Long id){
        this.id = id;
    }

}

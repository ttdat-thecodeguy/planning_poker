package com.springboot.planning_poker.model.enity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Data @Table(name = "users") @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @Column(name = "email", unique = true)
    private String email;
    @NotNull
    @NotBlank
    @Column(name = "password")
    private String password;
    @NotNull
    @NotBlank
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

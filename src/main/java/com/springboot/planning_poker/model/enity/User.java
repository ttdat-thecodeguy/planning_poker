package com.springboot.planning_poker.model.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Data @Table(name = "users")

        /*
     game_table
	- id(encode(game_name))
	- game's name
	- voting
	- user_owner

	bảng play_game
	- id table
	- id nguoi dung
         */

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "display_name")
    private String displayName;
    private String photo;
    private boolean isSpector;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<GameTable> tables = new HashSet<>();

    public User(Long id){
        this.id = id;
    }

}

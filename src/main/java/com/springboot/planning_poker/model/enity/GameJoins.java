package com.springboot.planning_poker.model.enity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "game_joins") @Data
@AllArgsConstructor
@NoArgsConstructor
public class GameJoins implements Serializable {

    @EmbeddedId
    private GameJoinId id;

    private String item;

    @Column(columnDefinition = "bool DEFAULT FALSE")
    private boolean isFlip = false;
    @Column(columnDefinition = "bool DEFAULT FALSE")
    private boolean isSpectator = false;
}

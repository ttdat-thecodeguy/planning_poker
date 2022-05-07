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

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne(targetEntity = GameTable.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "table_id", insertable = false, updatable = false)
    private GameTable gameTable;
}

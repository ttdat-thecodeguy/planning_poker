package com.springboot.planning_poker.model.dto.impl;

import com.springboot.planning_poker.model.enity.GameJoinId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class GameJoinsDTO {
    private GameJoinId id;
    private String item;
    private boolean isFlip;
    private boolean isSpectator;

    private GameTableDTO gameTable;
    private UserDTO user;
}

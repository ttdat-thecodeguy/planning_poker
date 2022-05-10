package com.springboot.planning_poker.model.enity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameJoinId implements Serializable {
    @Column(name = "table_id")
    private String tableId;
    @Column(name = "user_id")
    private Long userId;
}

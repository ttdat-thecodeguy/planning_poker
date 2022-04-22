package com.springboot.planning_poker.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class TableUpdate {
    private String tableId;
    private Long userId;
}

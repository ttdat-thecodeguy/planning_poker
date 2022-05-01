package com.springboot.planning_poker.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TableUpdate {
    private String tableId;
    private Long userId;
}

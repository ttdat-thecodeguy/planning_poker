package com.springboot.planning_poker.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class TableUpdateUser {
    private String tableId;
    private Long userId;
}

package com.springboot.planning_poker.model.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserDTO {
    private Long id;
    private String displayName;
//    private boolean isSpector;
}

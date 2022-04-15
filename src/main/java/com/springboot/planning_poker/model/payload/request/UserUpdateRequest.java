package com.springboot.planning_poker.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class UserUpdateRequest {
    private Long id;
    private String email;
    private String password;
    private String displayName;
}

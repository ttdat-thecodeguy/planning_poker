package com.springboot.planning_poker.model.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor @Data @Builder
public class UserUpdateRequest {
    private Long id;
    private String email;
    private String password;
    private String newPassword;
    private String displayName;
}

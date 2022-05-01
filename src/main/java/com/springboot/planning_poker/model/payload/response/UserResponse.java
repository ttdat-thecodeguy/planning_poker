package com.springboot.planning_poker.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserResponse {
    private Long id;
    private String token;
    private String email;
    private boolean isGuest;
    private String displayName;
    private String refreshToken;
    private boolean isSpectorMode;
}

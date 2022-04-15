package com.springboot.planning_poker.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginResponse {
    private String token;
}

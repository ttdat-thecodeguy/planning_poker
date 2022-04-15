package com.springboot.planning_poker.model.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
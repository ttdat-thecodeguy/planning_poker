package com.springboot.planning_poker.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RefreshTokenResponse {
	private String token;
	private String refreshToken; 
}

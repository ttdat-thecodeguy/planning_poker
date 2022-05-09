package com.springboot.planning_poker.model.payload.response.error_response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

public class JwtErrorResponse extends ErrorResponse {
	// for jwt
	
	public JwtErrorResponse(HttpStatus status, String message, boolean isJwtExpired, boolean isRefreshTokenExpired) {
		super(status, message);
		this.isJwtExpired = isJwtExpired;
		this.isRefreshTokenExpired = isRefreshTokenExpired;
	}
	
	@Getter @Setter
	private boolean isJwtExpired;
	
	@Getter @Setter
	boolean isRefreshTokenExpired;
}

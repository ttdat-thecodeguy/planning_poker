package com.springboot.planning_poker.model.payload.response.error_message;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class JwtErrorMessage extends ErrorMessage{
	// for jwt
	
	public JwtErrorMessage(HttpStatus status, String message, boolean isJwtExpired, boolean isRefreshTokenExpired) {
		super(status, message);
		this.isJwtExpired = isJwtExpired;
		this.isRefreshTokenExpired = isRefreshTokenExpired;
	}
	
	@Getter @Setter
	private boolean isJwtExpired;
	
	@Getter @Setter
	boolean isRefreshTokenExpired;
}

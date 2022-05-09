package com.springboot.planning_poker.model.payload.response.error_response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

public class UsernameNotFoundResponse extends ErrorResponse {
	
	public UsernameNotFoundResponse(HttpStatus status, String message, boolean isUserNotFound) {
		super(status, message);
		this.isUserNotFound = isUserNotFound;
	}
	
	@Getter @Setter
	private boolean isUserNotFound; 
}

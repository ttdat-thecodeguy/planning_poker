package com.springboot.planning_poker.model.payload.response.error_message;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

public class UsernameNotFoundMessage extends ErrorMessage{
	
	public UsernameNotFoundMessage(HttpStatus status, String message, boolean isUserNotFound) {
		super(status, message);
		this.isUserNotFound = isUserNotFound;
	}
	
	@Getter @Setter
	private boolean isUserNotFound; 
}

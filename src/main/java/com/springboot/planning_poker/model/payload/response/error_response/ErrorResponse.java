package com.springboot.planning_poker.model.payload.response.error_response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class ErrorResponse {
	private HttpStatus status;
	private String message;
	}

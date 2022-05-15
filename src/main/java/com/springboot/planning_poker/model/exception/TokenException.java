package com.springboot.planning_poker.model.exception;

public class TokenException extends Exception{
	private static final long serialVersionUID = 1L;
	public TokenException(String token, String message) {
		super(String.format("failed at %s : %s", token, message));
	}
}

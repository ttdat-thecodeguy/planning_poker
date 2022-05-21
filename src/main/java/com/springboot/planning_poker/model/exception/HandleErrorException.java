package com.springboot.planning_poker.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@ControllerAdvice
public class HandleErrorException {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		if (ex instanceof NullPointerException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok((new HashMap<String, Object>() {
				private static final long serialVersionUID = 1L;
				{
					put("error", ex.getMessage());
				}
			}));
		}
	}
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<?> handleException(ResponseStatusException ex, HttpServletRequest request, HttpServletResponse response) {
		if(ex.getRawStatusCode() == HttpStatus.NOT_FOUND.value()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.badRequest().body(String.format("error %d: %s", ex.getRawStatusCode(), ex.getMessage()));
	}
}
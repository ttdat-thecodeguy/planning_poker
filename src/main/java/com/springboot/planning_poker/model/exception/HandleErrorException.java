package com.springboot.planning_poker.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandleErrorException {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handle(Exception ex, HttpServletRequest request, HttpServletResponse response) {
		if (ex instanceof NullPointerException) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok((new HashMap<String, Object>() {
				/**
					 *
					 */
				private static final long serialVersionUID = 1L;
				{
					put("error", ex.getMessage());
				}
			}));
		}
	}




//    @ResponseStatus(HttpStatus.NOT_FOUND)
//	@ExceptionHandler(value =  NotFoundException.class )
//	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//		Map<String, String> errors = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		});
//		return errors;
//	}
}
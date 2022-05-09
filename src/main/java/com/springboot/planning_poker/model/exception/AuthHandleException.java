package com.springboot.planning_poker.model.exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springboot.planning_poker.model.payload.response.error_response.ErrorResponse;
import com.springboot.planning_poker.model.payload.response.error_response.UsernameNotFoundResponse;

import io.jsonwebtoken.JwtException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component 
public class AuthHandleException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException, JwtException {
        
    	
    	ErrorResponse message = null;
    	
    	if(authException instanceof UsernameNotFoundException) {
    		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    		message = new UsernameNotFoundResponse(HttpStatus.INTERNAL_SERVER_ERROR, authException.getMessage(), true);
    	}
    	
    	Gson gson = new GsonBuilder().create();
    	String json = gson.toJson(message);
    	
    	response.setContentType("application/json");
   	 	response.getWriter().write(json);
   	 	return;
    	
//    	if(authException instanceof ExpiredJwtException) {
//    		
//    	}
    	
    	
    	
    }
}

package com.springboot.planning_poker.model.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springboot.planning_poker.model.payload.response.error_message.JwtErrorMessage;
import com.springboot.planning_poker.model.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component @Slf4j @ResponseBody
public class JwtFilter extends OncePerRequestFilter {
    @Autowired private JwtUtils jwtUtils;
    @Autowired private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearer_token = request.getHeader("Authorization");
        String email = null;
        String token = null;
        System.out.println(bearer_token);
        if(bearer_token != null && bearer_token.startsWith("Bearer ")){
            token = bearer_token.substring(7);
            try{
                 email = jwtUtils.getUsernameFromToken(token);
            } catch (ExpiredJwtException ex){
//            	 log.info("jwt is expired ");

            	
            	 JwtErrorMessage message = new JwtErrorMessage(HttpStatus.BAD_REQUEST, "token is expired", true, false);
            	 Gson gson = new GsonBuilder().create();
            	 String json = gson.toJson(message);
            	 
            	 response.setStatus(HttpStatus.BAD_REQUEST.value());
            	 response.setContentType("application/json");
            	 response.getWriter().write(json);
            	 
            	 return;
            }
        }
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if(jwtUtils.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken t = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                t.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(t);
            }
        }
        filterChain.doFilter(request, response);
    }
}

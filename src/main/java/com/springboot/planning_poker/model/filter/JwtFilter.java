package com.springboot.planning_poker.model.filter;

import com.springboot.planning_poker.model.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component @Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Autowired private JwtUtils jwtUtils;
    @Autowired private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearer_token = request.getHeader("Authorization");
        String email = null;
        String token = null;

        log.info(bearer_token);

        if(bearer_token != null && bearer_token.startsWith("Bearer ")){
            token = bearer_token.substring(7);
            try{
                 email = jwtUtils.getUsernameFromToken(token);
            } catch (IllegalArgumentException ex){
                log.error("unable to get jwt");
            } catch (ExpiredJwtException ex){
                log.error("jwt token has expired");
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

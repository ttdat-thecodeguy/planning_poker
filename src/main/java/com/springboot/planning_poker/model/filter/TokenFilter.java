package com.springboot.planning_poker.model.filter;

import com.springboot.planning_poker.model.payload.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class TokenFilter implements HandlerInterceptor {

    private UserResponse loginResponse;
    public TokenFilter(UserResponse loginResponse){
        this.loginResponse = loginResponse;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            loginResponse.setToken(token.substring(7));
        }
        return HandlerInterceptor.super.preHandle(
                request,
                response,
                handler
        );
    }


}

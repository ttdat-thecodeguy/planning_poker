package com.springboot.planning_poker.config;

import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.business.impl.UserService;
import com.springboot.planning_poker.model.filter.TokenFilter;
import com.springboot.planning_poker.model.payload.response.LoginResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Bean public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public LoginResponse loginResponse() {
        return new LoginResponse();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenFilter(loginResponse())).addPathPatterns("/api/user/**");
    }
}

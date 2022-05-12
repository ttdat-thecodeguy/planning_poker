package com.springboot.planning_poker.service;

import com.springboot.planning_poker.constant.TestConstant;
import com.springboot.planning_poker.model.business.impl.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    public void whenLoadByExistsEmail_shouldReturnUser(){
        String email = TestConstant.EMAIL_TEST;
        assertThat(authService.loadUserByUsername(email).getUsername()).isEqualTo(email);
    }

    @Test
    public void whenLoadByUndefinedEmail_shouldThrowUsernameNotFoundException(){
        assertThatThrownBy(() -> authService.loadUserByUsername(TestConstant.UNDEFINED_EMAIL_TEST)).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    public void whenLoadByEmptyEmail_shouldThrowUsernameNotFoundException(){
        assertThatThrownBy(() -> authService.loadUserByUsername("")).isInstanceOf(UsernameNotFoundException.class);
    }
}

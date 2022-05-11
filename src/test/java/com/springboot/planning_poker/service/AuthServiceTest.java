package com.springboot.planning_poker.service;

import com.springboot.planning_poker.constant.Constant;
import com.springboot.planning_poker.model.business.IRole;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.business.impl.AuthService;
import com.springboot.planning_poker.model.business.impl.RefreshTokenService;
import com.springboot.planning_poker.model.business.impl.UserService;
import com.springboot.planning_poker.model.definition.RoleEnum;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.responsitory.RoleRepo;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import com.springboot.planning_poker.model.utils.JwtUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    public void whenLoadByExistsEmail_shouldReturnUser(){
        String email = Constant.EMAIL_TEST;
        assertThat(authService.loadUserByUsername(email).getUsername()).isEqualTo(email);
    }

    @Test
    public void whenLoadByUndefinedEmail_shouldThrowUsernameNotFoundException(){
        assertThatThrownBy(() -> authService.loadUserByUsername(Constant.UNDEFINED_EMAIL_TEST)).isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    public void whenLoadByEmptyEmail_shouldThrowUsernameNotFoundException(){
        assertThatThrownBy(() -> authService.loadUserByUsername("")).isInstanceOf(UsernameNotFoundException.class);
    }
}

package com.springboot.planning_poker.config;

import com.springboot.planning_poker.model.business.impl.AuthService;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@TestConfiguration
@ExtendWith(SpringExtension.class)
public class TestServiceConfiguration {
    @Bean(name = "authService")
    @Primary
    AuthService authService(){
        return new AuthService();
    }
}

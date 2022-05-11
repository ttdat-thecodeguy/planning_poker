package com.springboot.planning_poker;

import com.springboot.planning_poker.model.business.IRole;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.business.impl.AuthService;
import com.springboot.planning_poker.model.business.impl.UserService;
import com.springboot.planning_poker.model.definition.RoleEnum;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.enity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlanningPokerApplicationTests {


    @BeforeAll
    public void initData(){

    }

    @Test
    void contextLoads() {
    }
}

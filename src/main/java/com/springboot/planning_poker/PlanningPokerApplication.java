package com.springboot.planning_poker;

import com.springboot.planning_poker.model.business.IRole;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.responsitory.GameJoinsRepo;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
@Slf4j
public class PlanningPokerApplication implements CommandLineRunner {

    @Autowired private UserRepo userRepo;

     @Autowired private IUser userBus;
     @Autowired private IRole roleBus;
     @Autowired private ITable tableBus;
     @Autowired private PasswordEncoder passwordEncoder;
     @Autowired private TableRepo tableRepo;


     @Autowired private GameJoinsRepo gameJoinsRepo;

    public static void main(String[] args) {
        SpringApplication.run(PlanningPokerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        roleBus.addRole(new Role(1, "ROLE_USER"));
        roleBus.addRole(new Role(2, "ROLE_ADMIN"));
    }
}

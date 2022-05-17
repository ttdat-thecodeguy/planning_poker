package com.springboot.planning_poker;

import com.springboot.planning_poker.model.business.IRole;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.Constants;
import com.springboot.planning_poker.model.definition.RoleEnum;
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
import java.util.Set;

@SpringBootApplication
@Slf4j
public class PlanningPokerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PlanningPokerApplication.class, args);
    }
    @Autowired private IRole roleBus;
    @Autowired private IUser userBus;
    @Autowired private PasswordEncoder encoder;
    @Override
    public void run(String... args) throws Exception {
        Role roleUser = new Role(RoleEnum.ROLE_USER);
        Role roleAdmin = new Role(RoleEnum.ROLE_ADMIN);
        roleBus.addRole(roleUser);
        roleBus.addRole(roleAdmin);
        User user = User.builder().id(null).displayName("testA").email("test@123.com").password(encoder.encode("123")).roles(Set.of(roleAdmin)).build();
        userBus.addUser(user);
        User userUpdate = User.builder().id(null).displayName("testB").email("test@update.com").password(encoder.encode("123")).roles(Set.of(roleAdmin)).build();
        userBus.addUser(userUpdate);
    }
}

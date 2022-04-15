package com.springboot.planning_poker;

import com.springboot.planning_poker.model.business.IRole;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.responsitory.TableRepo;
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

     @Autowired private  IUser userBus;
     @Autowired private IRole roleBus;
     @Autowired private ITable tableBus;
     @Autowired private PasswordEncoder passwordEncoder;
     @Autowired private TableRepo tableRepo;
    public static void main(String[] args) {
        SpringApplication.run(PlanningPokerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User u1 = userBus.addUser(new User(null, "123@gmail.com",passwordEncoder.encode("1234"),"nam","user_default.jpg",false, new HashSet<>(), new HashSet<>()));
        User u2 = userBus.addUser(new User(null, "234@gmail.com",passwordEncoder.encode("1234"),"Hai","user_default.jpg",false, new HashSet<>(), new HashSet<>()));
        //roles
        roleBus.addRole(new Role(1, "ROLE_USER"));
        roleBus.addRole(new Role(2, "ROLE_ADMIN"));

        userBus.addRoleToUser(u1.getId(), 1);
        userBus.addRoleToUser(u2.getId(), 2);

        GameTable table = tableBus.addTable(new GameTable(null, "aaa", "1,2,4,8,10",new User(Long.valueOf(1)), new HashSet<>(), new HashSet<>()));
        //table.getIssues().add()
        log.info(String.valueOf(passwordEncoder.matches("1234", "$2a$10$AMadbQjo4dGLldNypEfqzuV0X8M9V71P1ku7hl8xf1mwhDRNqrDta")));
    }
}

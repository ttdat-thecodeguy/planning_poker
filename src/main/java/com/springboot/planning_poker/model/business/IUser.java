package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.LoginRequest;
import com.springboot.planning_poker.model.payload.request.UserUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface IUser extends UserDetailsService {
    List<User> getUsers();
    User findUserByEmail(String email);
     User getUserById(Long id);
     User addUser(User user);
     void updateUser(UserUpdateRequest user);
     void deleteUser(Long id);
     String login(@RequestBody LoginRequest userLogin);
     String signup(@RequestBody User user);
     void addRoleToUser(Long userid, Integer role_id);
}

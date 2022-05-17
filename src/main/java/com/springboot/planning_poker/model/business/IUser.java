package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.LoginRequest;
import com.springboot.planning_poker.model.payload.request.UserUpdateRequest;
import com.springboot.planning_poker.model.payload.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface IUser{
     List<User> getUsers();
     User findUserByEmail(String email);
     User findUserById(Long id);
     User addUser(User user);
     User updateUser(UserUpdateRequest user) throws Exception;
     void deleteUser(Long id);
     UserResponse login(LoginRequest userLogin) throws Exception;
     UserResponse signup(@RequestBody User user) throws Exception;
     UserResponse signupAsGuest(@RequestBody User user, Boolean isSpectator);
     void addRoleToUser(Long userid, Integer role_id);
}

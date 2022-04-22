package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.RoleEnum;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.LoginRequest;
import com.springboot.planning_poker.model.payload.response.UserResponse;
import com.springboot.planning_poker.model.responsitory.RoleRepo;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import com.springboot.planning_poker.model.payload.request.UserUpdateRequest;
import com.springboot.planning_poker.model.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component @Slf4j
public class UserService implements IUser {

     @Autowired private UserRepo userRepo;
     @Autowired private RoleRepo roleRepo;
     @Autowired private TableRepo tableRepo;

     @Autowired private JwtUtils jwtUtils;
     @Autowired private PasswordEncoder encoder;
     @Autowired private AuthenticationManager authenticationManager;
    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return findUserByEmail(email);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.getById(id);
    }

    @Override @Transactional
    public User addUser(User user) {
         return userRepo.save(user);
    }

    @Override @Transactional
    public void updateUser(UserUpdateRequest userUpdate) {
        User user = userRepo.findById(userUpdate.getId()).get();
        if(userUpdate.getEmail() != null) user.setEmail(userUpdate.getEmail());
        if(userUpdate.getDisplayName() != null) user.setDisplayName(userUpdate.getDisplayName());
        if(userUpdate.getPassword() != null) {
            if(encoder.matches(userUpdate.getPassword(), user.getPassword()))
                user.setPassword(userUpdate.getPassword());
        }
        userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
         userRepo.deleteById(id);
    }

    @Override
    public UserResponse login(LoginRequest userLogin) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userLogin.getEmail(), userLogin.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        log.info(userLogin.getEmail());
        User user = userRepo.findUserByEmail(userLogin.getEmail());
        String token = jwtUtils.generateToken(user.getEmail(), user.getId());
        return new UserResponse(user.getId(),token, user.getEmail(), false, user.getDisplayName(), false);
    }

    @Override @Transactional
    public UserResponse signup(User user) throws Exception {
        if(userRepo.findUserByEmail(user.getEmail()) != null){
            throw new Exception("User is registered, Please choose another email");
        }
        user.getRoles().add(new Role(RoleEnum.ROLE_USER));
        user.setPassword(encoder.encode(user.getPassword()));
        User userSave = userRepo.save(user);
        String token = jwtUtils.generateToken(userSave.getEmail(), user.getId());
        return new UserResponse(user.getId() ,token, user.getEmail(), false, userSave.getDisplayName(), false);
    }

    @Override @Transactional
    public UserResponse signupAsGuest(User user, Boolean isSpectator, String tableid) {
        GameTable table = tableRepo.getById(tableid);
        //check is spectator
        isSpectator = isSpectator != null && isSpectator;
        // set attr to user
        user.getRoles().add(new Role(RoleEnum.ROLE_USER));
        user.setPassword(encoder.encode(String.valueOf(Math.random())));
        user.setEmail(RandomStringUtils.random(5, true, false) + "@gmail.com");
        user.setSpector(isSpectator);
        User userSave = userRepo.save(user);
        table.getJoins().add(userSave);
        //generate token from random email and id
        String token = jwtUtils.generateToken(user.getEmail(), user.getId());
        return new UserResponse(user.getId(), token, user.getEmail(), true, userSave.getDisplayName(), isSpectator);
    }

    @Override @Transactional
    public void addRoleToUser(Long userid, Integer role_id) {
        User user = userRepo.getById(userid);
        Role role = roleRepo.getById(role_id);
        user.getRoles().add(role);
    }
}

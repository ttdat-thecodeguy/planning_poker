package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.RoleEnum;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.LoginRequest;
import com.springboot.planning_poker.model.responsitory.RoleRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import com.springboot.planning_poker.model.payload.request.UserUpdateRequest;
import com.springboot.planning_poker.model.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component @Transactional
public class UserService implements IUser {

     @Autowired private UserRepo userRepo;
     @Autowired private RoleRepo roleRepo;

     @Autowired private JwtUtils jwtUtils;
     @Autowired private PasswordEncoder encoder;

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

    @Override
    public User addUser(User user) {
         return userRepo.save(user);
    }

    @Override
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
    public String login(LoginRequest userLogin) {
        return null;
    }

    @Override
    public String signup(User user) {
        user.getRoles().add(new Role(RoleEnum.ROLE_USER));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
        String token = jwtUtils.generateToken(user.getEmail());
        return token;
    }

    @Override
    public void addRoleToUser(Long userid, Integer role_id) {
        User user = userRepo.getById(userid);
        Role role = roleRepo.getById(role_id);
        user.getRoles().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail(email);
        if(user != null){
            List<SimpleGrantedAuthority> grantedAuthorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
        } else{
            throw new UsernameNotFoundException("User Not Found In DB");
        }
    }
}

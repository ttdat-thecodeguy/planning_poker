package com.springboot.planning_poker.service;

import com.springboot.planning_poker.constant.Constant;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.StatusCode;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.LoginRequest;
import com.springboot.planning_poker.model.payload.response.UserResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith({SpringExtension.class})
@SpringBootTest @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    @Autowired private IUser userBus;
    @Autowired private PasswordEncoder encoder;
    /*getList*/
    @Test
    public void whenGetUsers_shouldReturnList(){
        assertThat(userBus.getUsers().size()).isEqualTo(1);
    }
    /*FindEmail*/
    @Test
    public void whenFindUserByExistsEmail_shouldReturnUser(){

        assertThat(userBus.findUserByEmail(Constant.EMAIL_TEST).getEmail()).isEqualTo(Constant.EMAIL_TEST);
    }
    @Test
    public void whenFindUserByNotExistsEmail_shouldThrowUsernameNotFoundException(){
        assertThatThrownBy(() -> userBus.findUserByEmail(Constant.UNDEFINED_EMAIL_TEST)).isInstanceOf(UsernameNotFoundException.class);
    }
    @Test
    public void whenFindUserByNullEmail_shouldThrowUsernameNotFoundException(){
        assertThatThrownBy(() -> userBus.findUserByEmail(null)).isInstanceOf(UsernameNotFoundException.class);
    }
    @Test
    public void whenFindUserByEmptyEmail_shouldThrowUsernameNotFoundException(){
        assertThatThrownBy(() -> userBus.findUserByEmail("")).isInstanceOf(UsernameNotFoundException.class);
    }

    /*Add User*/
    @Test
    public void whenAddUserValid_returnUser(){
        User userValid = User.builder().id(null).displayName("aaa").email("valid@123.com").password(encoder.encode(Constant.PASSWORD_TEST)).build();
        assertThat(userBus.addUser(userValid)).isInstanceOf(User.class);
    }
    @Test
    public void whenAddUserSameEmail_returnUser(){
        User userValid = User.builder().id(null).displayName("bbb").email(Constant.EMAIL_TEST).password(encoder.encode(Constant.PASSWORD_TEST)).build();
        assertThatThrownBy(() -> userBus.addUser(userValid)).isInstanceOf(DataIntegrityViolationException.class);
    }
    /*TODO Update User*/
    @Test
    public void updateUser(){

    }
    /*TODO delete user*/

    /*Login*/
    @Test
    public void whenLoginValid_shouldReturnUserResponse() throws Exception {
        assertThat(userBus.login(LoginRequest.builder().email(Constant.EMAIL_TEST).password(Constant.PASSWORD_TEST).build())).isInstanceOf(UserResponse.class);
    }
    @Test
    public void whenLoginWithUndefinedEmail_shouldHasMessage() throws Exception {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email(Constant.UNDEFINED_EMAIL_TEST).password(Constant.PASSWORD_TEST).build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    @Test
    public void whenLoginWithWrongPassword_shouldHasMessage() throws Exception {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email(Constant.EMAIL_TEST).password("123456").build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    @Test
    public void whenLoginWithEmptyEmail_shouldHasMessage() throws Exception {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email("").password("123456").build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    @Test
    public void whenLoginWithEmptyPassword_shouldHasMessage() throws Exception {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email(Constant.EMAIL_TEST).password("").build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    @Test
    public void whenLoginWithEmptyEmailAndPassword_shouldHasMessage() throws Exception {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email("").password("").build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    /*TODO signUp*/

    /*TODO signUpAsGuest*/

    /*addRoleToUser*/
    @Test
    public void whenAddValidRoleIdAndUserId_shouldNotException(){
        User userValid = userBus.findUserByEmail(Constant.EMAIL_TEST);
        assertDoesNotThrow(() -> userBus.addRoleToUser(userValid.getId(), 1));
    }
    @Test
    public void whenAddUndefinedRoleId_shouldThrowResponseStatusException(){
        Role roleUndefined = new Role(3, "UNDEFINED_ROLE");
        User userValid = userBus.findUserByEmail(Constant.EMAIL_TEST);
        assertThatThrownBy(() -> userBus.addRoleToUser(userValid.getId(), roleUndefined.getId())).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenAddUndefinedRoleIdAndAddUndefinedUserId_shouldThrowResponseStatusException(){
        assertThatThrownBy(() -> userBus.addRoleToUser(Long.MAX_VALUE, Integer.MAX_VALUE)).isInstanceOf(ResponseStatusException.class);
    }
}

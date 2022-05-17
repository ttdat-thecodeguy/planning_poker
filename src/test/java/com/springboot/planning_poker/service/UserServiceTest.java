package com.springboot.planning_poker.service;

import com.springboot.planning_poker.constant.TestConstant;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.RoleEnum;
import com.springboot.planning_poker.model.definition.StatusCode;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.LoginRequest;
import com.springboot.planning_poker.model.payload.request.UserUpdateRequest;
import com.springboot.planning_poker.model.payload.response.UserResponse;

import org.junit.jupiter.api.BeforeEach;
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


import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class UserServiceTest {
    @Autowired private IUser userBus;
    @Autowired private PasswordEncoder encoder;
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    /*getList*/
    @Test
    public void whenGetUsers_shouldReturnList(){
        /*have one user in database so list_users >= 1*/
        /*some method add below affect to this result*/
        assertThat(userBus.getUsers().size()).isGreaterThanOrEqualTo(1);
    }
    /*FindEmail*/
    @Test
    public void whenFindUserByExistsEmail_shouldReturnUser(){

        assertThat(userBus.findUserByEmail(TestConstant.EMAIL_TEST).getEmail()).isEqualTo(TestConstant.EMAIL_TEST);
    }
    @Test
    public void whenFindUserByNotExistsEmail_shouldThrowUsernameNotFoundException(){
        assertThatThrownBy(() -> userBus.findUserByEmail(TestConstant.UNDEFINED_EMAIL_TEST)).isInstanceOf(UsernameNotFoundException.class);
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
        User userValid = User.builder().id(null).displayName("aaa").email("valid@123.com").password(encoder.encode(TestConstant.PASSWORD_TEST)).build();
        assertThat(userBus.addUser(userValid)).isInstanceOf(User.class);
    }
    @Test
    public void whenAddUserSameEmail_returnUser(){
        User userValid = User.builder().id(null).displayName("bbb").email(TestConstant.EMAIL_TEST).password(encoder.encode(TestConstant.PASSWORD_TEST)).build();
        assertThatThrownBy(() -> userBus.addUser(userValid)).isInstanceOf(DataIntegrityViolationException.class);
    }
    /*Update User*/
    @Test
    public void updateUser_email_returnUserWithEmailUpdated() throws Exception {
        User userUpdate = userBus.findUserByEmail("test@update.com");
        assertThat(userBus.updateUser(UserUpdateRequest.builder().id(userUpdate.getId()).email("test@updated.com").build()).getEmail()).isEqualTo("test@updated.com");
    }

    @Test
    public void updateUser_email_emailIsExists_returnUserWithEmailUpdated(){
        User userUpdate = userBus.findUserByEmail("test@update.com");
        assertThatThrownBy(() -> userBus.updateUser(UserUpdateRequest.builder().id(userUpdate.getId()).email(TestConstant.EMAIL_TEST).build())).isInstanceOf(Exception.class).hasMessage(StatusCode.EMAIL_EXISTS);
    }

    @Test
    public void updateUser_password_returnUserWithNewPassword() throws Exception {
        User userUpdate = userBus.findUserByEmail("test@update.com");
        User userChange = userBus.updateUser(UserUpdateRequest.builder().id(userUpdate.getId()).password("123").newPassword("12345").build());
        assertThat(encoder.matches("12345", userChange.getPassword())).isTrue();
    }

    @Test
    public void updateUser_password_passwordNotMatch_throwException(){
        User userUpdate = userBus.findUserByEmail("test@update.com");
        assertThatThrownBy(() -> userBus.updateUser(UserUpdateRequest.builder().id(userUpdate.getId()).password("123").newPassword("12345").build())).isInstanceOf(Exception.class).hasMessage(StatusCode.PASSWORD_NOT_MATCH);
    }

    @Test
    public void updateUser_displayName_returnUserWithDisplayName() throws Exception {
        /// mock data
        User userUpdate = User.builder().id(null).displayName("test2").email("test@update2.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build();
        userBus.addUser(userUpdate);

        assertThat(userBus.updateUser(UserUpdateRequest.builder().id(userUpdate.getId()).displayName("aaa").build()).getDisplayName()).isEqualTo("aaa");
    }
    /*Login*/
    @Test
    public void whenLoginValid_shouldReturnUserResponse() throws Exception {
        assertThat(userBus.login(LoginRequest.builder().email(TestConstant.EMAIL_TEST).password(TestConstant.PASSWORD_TEST).build())).isInstanceOf(UserResponse.class);
    }
    @Test
    public void whenLoginWithUndefinedEmail_shouldHasMessage() {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email(TestConstant.UNDEFINED_EMAIL_TEST).password(TestConstant.PASSWORD_TEST).build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    @Test
    public void whenLoginWithWrongPassword_shouldHasMessage() {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email(TestConstant.EMAIL_TEST).password("123456").build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    @Test
    public void whenLoginWithEmptyEmail_shouldHasMessage() {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email("").password("123456").build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    @Test
    public void whenLoginWithEmptyPassword_shouldHasMessage() {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email(TestConstant.EMAIL_TEST).password("").build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    @Test
    public void whenLoginWithEmptyEmailAndPassword_shouldHasMessage() {
        assertThatThrownBy(() -> userBus.login(LoginRequest.builder().email("").password("").build())).isInstanceOf(Exception.class).hasMessage(StatusCode.INVALID_CREDENTIALS);
    }
    /*signUp*/
    @Test
    public void whenSignUpWithValidUser_shouldReturnUserResponse() throws Exception {
        User user = new User(null, "valid_user_1@gmail.com"  ,"123","abc",null,false, new HashSet<>(), null);
        assertThat(userBus.signup(user)).isInstanceOf(UserResponse.class);
    }
    @Test
    public void whenSignUpUserWithEmptyEmail_returnConstraintViolation() {
        User userInvalid = new User(null, "","123","abc",null,false, new HashSet<>(), null);
        Set<ConstraintViolation<User>> violations = validator.validate(userInvalid);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void whenSignUpUserWithEmptyPassword_returnConstraintViolation(){
        User userInvalid = new User(null, TestConstant.UNDEFINED_EMAIL_TEST,"","abc",null,false, new HashSet<>(), null);
        Set<ConstraintViolation<User>> violations = validator.validate(userInvalid);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void whenSignUpUserWithEmptyEmailAndPassword_returnConstraintViolation(){
        User userInvalid = new User(null, "","","abc",null,false, new HashSet<>(), null);
        Set<ConstraintViolation<User>> violations = validator.validate(userInvalid);
        assertThat(violations.size()).isEqualTo(2);
    }
    /*signUpAsGuest*/
    @Test
    public void whenSignUpUserAsGuestValid_returnUserResponse(){
        User userValid = User.builder().displayName("aaaa").roles(new HashSet<>()).build();
        assertThat(userBus.signupAsGuest(userValid, false)).isInstanceOf(UserResponse.class);
    }
    @Test
    public void whenSignUpUserAsGuestValidWithSpectatorNull_returnUserResponse(){
        User userValid = User.builder().displayName("bbbb").roles(new HashSet<>()).build();
        assertThat(userBus.signupAsGuest(userValid, null)).isInstanceOf(UserResponse.class);
    }
    /*addRoleToUser*/
    @Test
    public void whenAddValidRoleIdAndUserId_shouldNotException(){
        User userValid = userBus.findUserByEmail(TestConstant.EMAIL_TEST);
        assertDoesNotThrow(() -> userBus.addRoleToUser(userValid.getId(), 1));
    }
    @Test
    public void whenAddUndefinedRoleId_shouldThrowResponseStatusException(){
        Role roleUndefined = new Role(10000, "UNDEFINED_ROLE");
        User userValid = userBus.findUserByEmail(TestConstant.EMAIL_TEST);
        assertThatThrownBy(() -> userBus.addRoleToUser(userValid.getId(), roleUndefined.getId())).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenAddUndefinedRoleIdAndAddUndefinedUserId_shouldThrowResponseStatusException(){
        assertThatThrownBy(() -> userBus.addRoleToUser(Long.MAX_VALUE, Integer.MAX_VALUE)).isInstanceOf(ResponseStatusException.class);
    }
}

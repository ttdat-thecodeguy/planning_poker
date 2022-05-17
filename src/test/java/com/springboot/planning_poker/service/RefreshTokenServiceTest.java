package com.springboot.planning_poker.service;

import com.springboot.planning_poker.constant.TestConstant;
import com.springboot.planning_poker.model.business.IRefreshToken;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.RoleEnum;
import com.springboot.planning_poker.model.enity.RefreshToken;
import com.springboot.planning_poker.model.enity.Role;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.exception.TokenException;
import com.springboot.planning_poker.model.payload.request.LoginRequest;
import com.springboot.planning_poker.model.payload.response.RefreshTokenResponse;
import com.springboot.planning_poker.model.payload.response.UserResponse;
import com.springboot.planning_poker.model.responsitory.RefreshedTokenRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class RefreshTokenServiceTest {
    @Autowired private IRefreshToken refreshTokenBus;
    @Autowired private IUser userBus;
    UserResponse userResponse;
    @Autowired private PasswordEncoder encoder;
    @Autowired private RefreshedTokenRepo refreshedTokenRepo;
    User user;
    @PostConstruct
    public void initData() throws Exception {
        user = userBus.findUserByEmail(TestConstant.EMAIL_TEST);
        userResponse = userBus.login(LoginRequest.builder().email(user.getEmail()).password("123").build());
    }

    @Test
    public void whenFindRefreshToken_existsToken_returnRefreshToken() throws TokenException {
        assertThat(refreshTokenBus.findByToken(userResponse.getRefreshToken())).isInstanceOf(RefreshToken.class);
    }

    @Test
    public void whenFindRefreshToken_NotExistsToken_throwTokenException(){
        assertThatThrownBy(() -> refreshTokenBus.findByToken("")).isInstanceOf(TokenException.class);
    }

    @Test
    public void whenCreateRefreshToken_returnRefreshToken(){
        assertThat(refreshTokenBus.createRefreshToken(1L)).isInstanceOf(RefreshToken.class);
    }

    @Test
    public void whenCreateRefreshToken_NotExistsUserId_throwResourceStatusException(){
        assertThatThrownBy(() -> refreshTokenBus.createRefreshToken(1000L)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void whenVerifyExpiredDate_NotYetExpiredDate_returnRefreshToken() throws TokenException {
        // mock
        RefreshToken token = this.refreshTokenBus.findByToken(userResponse.getRefreshToken());

        assertThat(refreshTokenBus.verifyExpiration(token));
    }

    @Test
    public void whenVerifyExpiredDate_isExpiredDate_throwTokenException(){
        //// mock data
        User user = User.builder().id(null).displayName("testA").email("test@hhhhhh.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build();
        userBus.addUser(user);
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().minusMillis(10000));
        refreshedTokenRepo.save(token);

        assertThatThrownBy(() -> refreshTokenBus.verifyExpiration(token)).isInstanceOf(TokenException.class);
    }

    @Test
    public void whenRefreshToken_returnRefreshTokenResponse() throws TokenException {
        assertThat(refreshTokenBus.refreshToken(userResponse.getRefreshToken())).isInstanceOf(RefreshTokenResponse.class);
    }

}

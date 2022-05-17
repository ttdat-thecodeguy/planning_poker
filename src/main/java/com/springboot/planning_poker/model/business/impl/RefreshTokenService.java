package com.springboot.planning_poker.model.business.impl;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.springboot.planning_poker.model.definition.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.planning_poker.model.business.IRefreshToken;
import com.springboot.planning_poker.model.enity.RefreshToken;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.exception.TokenException;
import com.springboot.planning_poker.model.payload.response.RefreshTokenResponse;
import com.springboot.planning_poker.model.responsitory.RefreshedTokenRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import com.springboot.planning_poker.model.utils.JwtUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor @Service
public class RefreshTokenService implements IRefreshToken{
	private final RefreshedTokenRepo refreshTokenRepo;
	private final UserRepo userRepo;
	
	@Value("${app.refreshed_time}")
	private long refreshTime;
	
	@Autowired private JwtUtils jwtUtils;
	
	@Override
	public RefreshToken findByToken(String token) throws TokenException {
		RefreshToken refreshToken = refreshTokenRepo.findByToken(token).orElse(null);
		if(refreshToken == null) throw new TokenException(token , StatusCode.REFRESH_TOKEN_NOT_FOUND);
		return refreshToken;
	}

	@Override
	public RefreshToken createRefreshToken(Long userId) {
		User user = userRepo.findById(userId).orElse(null);
		if(user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusCode.USER_NOT_FOUND);
		RefreshToken token = new RefreshToken();
		token.setUser(user);
		token.setToken(UUID.randomUUID().toString());
		token.setExpiryDate(Instant.now().plusMillis(refreshTime));
		return refreshTokenRepo.save(token);
	}

	@Override
	public RefreshToken verifyExpiration(RefreshToken refreshToken) throws TokenException {
		if(refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepo.delete(refreshToken);
			throw new TokenException(refreshToken.getToken(), StatusCode.REFRESH_TOKEN_EXPIRED);
		}
		return refreshToken;
	}

	@Override
	public RefreshTokenResponse refreshToken(String refreshToken) throws TokenException {
		RefreshToken rToken = findByToken(refreshToken);
		verifyExpiration(rToken);
		String token = jwtUtils.generateToken(rToken.getUser().getEmail(), rToken.getUser().getId());
		return new RefreshTokenResponse(token, rToken.getToken());
		
	}

}

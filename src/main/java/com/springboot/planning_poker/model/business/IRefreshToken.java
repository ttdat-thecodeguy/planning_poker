package com.springboot.planning_poker.model.business;

import java.util.Optional;

import com.springboot.planning_poker.model.enity.RefreshToken;
import com.springboot.planning_poker.model.exception.TokenException;
import com.springboot.planning_poker.model.payload.response.RefreshTokenResponse;

public interface IRefreshToken {
	 RefreshToken findByToken(String token) throws TokenException;
	 RefreshToken createRefreshToken(Long userId);
	 RefreshTokenResponse refreshToken(String token) throws TokenException;
	 RefreshToken verifyExpiration(RefreshToken token) throws TokenException;
}

package com.springboot.planning_poker.model.business;

import java.util.Optional;

import com.springboot.planning_poker.model.enity.RefreshToken;
import com.springboot.planning_poker.model.exception.TokenException;
import com.springboot.planning_poker.model.payload.response.RefreshTokenResponse;

public interface IRefreshToken {
	public RefreshToken findByToken(String token) throws TokenException;
	public RefreshToken createRefreshToken(Long userId);
	public RefreshTokenResponse refreshToken(String token) throws TokenException;
	public RefreshToken verifyExpiration(RefreshToken token) throws TokenException;
}

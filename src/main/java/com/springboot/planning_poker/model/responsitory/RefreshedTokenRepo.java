package com.springboot.planning_poker.model.responsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.planning_poker.model.enity.RefreshToken;

@Repository
public interface RefreshedTokenRepo extends JpaRepository<RefreshToken, Long>{
	Optional<RefreshToken> findByToken(String token);
}

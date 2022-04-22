package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.enity.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepo extends JpaRepository<Voting, Long> {
}

package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.IVoting;
import com.springboot.planning_poker.model.enity.Voting;
import com.springboot.planning_poker.model.responsitory.VotingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VotingService implements IVoting {

    @Autowired private VotingRepo votingRepo;

    @Override
    public Voting addVoting(Voting voting) {

        return votingRepo.save(voting);
    }
}

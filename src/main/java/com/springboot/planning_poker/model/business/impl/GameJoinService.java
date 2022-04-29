package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.enity.GameJoinId;
import com.springboot.planning_poker.model.enity.GameJoins;
import com.springboot.planning_poker.model.responsitory.GameJoinsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;

@Service
public class GameJoinService implements IGameJoins {

    @Autowired private GameJoinsRepo gameJoinsRepo;

    @Override
    public List<Tuple> getDetailsOfTable(String id) {
        return gameJoinsRepo.findAllById_TableId(id);
    }

    @Override
    public void deleteById(String gameTableId, Long userId) {
        gameJoinsRepo.deleteById(new GameJoinId(gameTableId, userId));
    }

    @Override
    public void updateGameWhenSelected(String tableId, Long userId, String item) {
        GameJoins gameJoins = gameJoinsRepo.findById(new GameJoinId(tableId, userId)).orElse(null);
        gameJoins.setItem(item);
        gameJoins.setFlip(true);
        gameJoinsRepo.save(gameJoins);
    }

    @Override
    public void updateGameWhenUnSelected(String tableId, Long userId) {
        GameJoins gameJoins = gameJoinsRepo.findById(new GameJoinId(tableId, userId)).orElse(null);
        gameJoins.setItem(null);
        gameJoins.setFlip(false);
        gameJoinsRepo.save(gameJoins);
    }

    @Override
    public void updateGameWhenSwitchSpectator(String tableId, Long userId, boolean isSpectator) {
        GameJoins gameJoins = gameJoinsRepo.findById(new GameJoinId(tableId, userId)).orElse(null);
        gameJoins.setItem(null);
        gameJoins.setFlip(false);
        gameJoins.setSpectator(isSpectator);
        gameJoinsRepo.save(gameJoins);
    }
}

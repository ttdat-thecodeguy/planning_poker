package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.dto.DeckCount;
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
<<<<<<< Updated upstream
        return gameJoinsRepo.findDetailsOfTableById_TableId(id);
    }

    @Override
    public DeckCount calculateGameResult(List<DeckCount> lstDeckCount) {
        /// TODO Not check
        var result = lstDeckCount.stream().filter(item -> item.getCount() > lstDeckCount.get(0).getCount()).findFirst().orElse(lstDeckCount.get(0));
        return result;
    }

    @Override
    public List<DeckCount> getGameResult(String tableId) {
=======
        return gameJoinsRepo.findAllAndDisplayNameById_TableId(id);
    }
    public List<Tuple> getGameResult(String tableId) {
>>>>>>> Stashed changes
        return gameJoinsRepo.countDeckInTable(tableId);
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

package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.dto.impl.DeckCountDTO;
import com.springboot.planning_poker.model.dto.impl.GameJoinsDTO;
import com.springboot.planning_poker.model.enity.GameJoinId;
import com.springboot.planning_poker.model.enity.GameJoins;
import com.springboot.planning_poker.model.responsitory.GameJoinsRepo;
import com.springboot.planning_poker.model.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameJoinService implements IGameJoins {

    @Autowired private GameJoinsRepo gameJoinsRepo;


    public List<GameJoinsDTO> getDetailOfTable(String id){
       // return gameJoinsRepo.findById_TableId(id);
        return ModelMapperUtils.mapAll(gameJoinsRepo.findById_TableId(id), GameJoinsDTO.class);
    }



    public List<DeckCountDTO> getGameResult(String tableId) {
        /// TODO Sẽ hỏi chỗ này sau - (làm sao để parse json bằng list interface hoặc chuyển sang list class nhanh)
        List<DeckCountDTO> lstDeckCountDTO = gameJoinsRepo.countDeckInTable(tableId).stream().map(DeckCountDTO::new).collect(Collectors.toList());
        return lstDeckCountDTO;
    }

    @Override
    public DeckCountDTO calculateGameResult(List<DeckCountDTO> gameDetails) {
        return gameDetails.stream().sorted(Comparator.comparing(DeckCountDTO::getCount)).findFirst().orElse(gameDetails.get(0));
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

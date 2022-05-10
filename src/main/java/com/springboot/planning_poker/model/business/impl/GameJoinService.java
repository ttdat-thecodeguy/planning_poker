package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.dto.DeckCountDTO;
import com.springboot.planning_poker.model.dto.GameJoinsDTO;
import com.springboot.planning_poker.model.enity.GameJoinId;
import com.springboot.planning_poker.model.enity.GameJoins;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.responsitory.GameJoinsRepo;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GameJoinService implements IGameJoins {

    @Autowired private GameJoinsRepo gameJoinsRepo;
    @Autowired private ITable gameTableBus;
    @Autowired private TableRepo gameTableRepo;

    public List<GameJoinsDTO> getDetailOfTable(String id){
        return ModelMapperUtils.mapAll(gameJoinsRepo.findById_TableId(id), GameJoinsDTO.class);
    }

    public List<DeckCountDTO> getGameResult(String tableId) {
       return gameJoinsRepo.countDeckInTable(tableId);
    }

    @Override
    public DeckCountDTO calculateGameResult(List<DeckCountDTO> gameDetails) {
        return gameDetails.stream().min(Comparator.comparing(DeckCountDTO::getCount)).orElse(gameDetails.get(0));
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

    public void changeShowCard(GameTable table){
        if(table.getIsShowCardByOwner()){
            table.setIsShowCardByOwner(false);
            gameTableRepo.save(table);
        }
    }

    @Override
    public boolean isUserOwnerLeave(Long userId, String tableId) {
        GameTable table = gameTableBus.findTableById(tableId);
        if(Objects.equals(userId, table.getUserOwerId())){
            this.changeShowCard(table);
            return true;
        } else{
            return false;
        }
    }
}

package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.definition.StatusCode;
import com.springboot.planning_poker.model.dto.DeckCountDTO;
import com.springboot.planning_poker.model.dto.GameJoinsDTO;
import com.springboot.planning_poker.model.enity.GameJoinId;
import com.springboot.planning_poker.model.enity.GameJoins;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.responsitory.GameJoinsRepo;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.utils.ModelMapperUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class GameJoinService implements IGameJoins {

    @Autowired private GameJoinsRepo gameJoinsRepo;
    @Autowired private ITable gameTableBus;
    @Autowired private TableRepo gameTableRepo;

    @Override
    public GameJoins findGameJoinsById(String tableId, Long userId) {
        GameJoins gameJoins = gameJoinsRepo.findById(new GameJoinId(tableId, userId)).orElse(null);
        if(gameJoins == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusCode.GAME_JOINS_NOT_FOUND);
        return gameJoins;
    }

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

    @Override @Transactional
    public void deleteById(String gameTableId, Long userId) {
        gameJoinsRepo.deleteById(new GameJoinId(gameTableId, userId));
    }

    @Override
    public GameJoins updateGameWhenSelected(String tableId, Long userId, String item) throws Exception {
        if(item == "" || item == null){
            throw new Exception(StatusCode.ITEM_INVALID);
        }
        GameJoins gameJoins = this.findGameJoinsById(tableId, userId);
        gameJoins.setItem(item);
        gameJoins.setFlip(true);
        return gameJoinsRepo.save(gameJoins);
    }

    @Override
    public GameJoins updateGameWhenUnSelected(String tableId, Long userId) {
        GameJoins gameJoins = this.findGameJoinsById(tableId, userId);
        gameJoins.setItem(null);
        gameJoins.setFlip(false);
        return gameJoinsRepo.save(gameJoins);
    }

    @Override
    public GameJoins updateGameWhenSwitchSpectator(String tableId, Long userId, boolean isSpectator) {
        GameJoins gameJoins = this.findGameJoinsById(tableId, userId);
        gameJoins.setItem(null);
        gameJoins.setFlip(false);
        gameJoins.setSpectator(isSpectator);
        return gameJoinsRepo.save(gameJoins);
    }

    @Override @Transactional
    public void resetGameJoins(String tableId) {
        this.gameJoinsRepo.resetGameJoins(tableId);
    }

    public void changeShowCard(@NotNull GameTable table){
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

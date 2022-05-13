package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.dto.DeckCountDTO;
import com.springboot.planning_poker.model.dto.GameJoinsDTO;
import com.springboot.planning_poker.model.enity.GameJoins;

import java.util.List;

public interface IGameJoins {
     GameJoins findGameJoinsById(String tableId, Long userId);

     List<GameJoinsDTO> getDetailOfTable(String id);

     List<DeckCountDTO> getGameResult(String tableId);

     DeckCountDTO calculateGameResult(List<DeckCountDTO> gameDetails);

     void deleteById(String gameTableId, Long userId);

     GameJoins updateGameWhenSelected(String tableId, Long userId, String item) throws Exception;

     GameJoins updateGameWhenUnSelected(String tableId, Long userId);

     GameJoins updateGameWhenSwitchSpectator(String tableId, Long userId, boolean isSpectator);

     void resetGameJoins(String tableId);

     boolean isUserOwnerLeave(Long userId, String tableId);
}

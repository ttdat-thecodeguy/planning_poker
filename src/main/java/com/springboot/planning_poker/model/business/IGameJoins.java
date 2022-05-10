package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.dto.DeckCountDTO;
import com.springboot.planning_poker.model.dto.GameJoinsDTO;

import java.util.List;

public interface IGameJoins {
    public List<GameJoinsDTO> getDetailOfTable(String id);
    public List<DeckCountDTO> getGameResult(String tableId);
    public DeckCountDTO calculateGameResult(List<DeckCountDTO> gameDetails);
    public void deleteById(String gameTableId, Long userId);
    public void updateGameWhenSelected(String tableId, Long userId, String item);
    public void updateGameWhenUnSelected(String tableId, Long userId);
    public void updateGameWhenSwitchSpectator(String tableId, Long userId, boolean isSpectator);
    public boolean isUserOwnerLeave(Long userId, String tableId);
}

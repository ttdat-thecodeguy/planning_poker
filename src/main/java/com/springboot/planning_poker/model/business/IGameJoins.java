package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.dto.DeckCount;
import com.springboot.planning_poker.model.enity.GameJoins;

import javax.persistence.Tuple;
import java.util.List;

public interface IGameJoins {
     List<Tuple> getDetailsOfTable(String id);
     DeckCount calculateGameResult(List<DeckCount> lstDeckCount);
     List<DeckCount> getGameResult(String tableId);
     void deleteById(String gameTableId, Long userId);
     void updateGameWhenSelected(String tableId, Long userId, String item);
     void updateGameWhenUnSelected(String tableId, Long userId);
     void updateGameWhenSwitchSpectator(String tableId, Long userId, boolean isSpectator);
}

package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.GameJoins;

import javax.persistence.Tuple;
import java.util.List;

public interface IGameJoins {
    public List<Tuple> getDetailsOfTable(String id);
    public void deleteById(String gameTableId, Long userId);
    public void updateGameWhenSelected(String tableId, Long userId, String item);
    public void updateGameWhenUnSelected(String tableId, Long userId);
    public void updateGameWhenSwitchSpectator(String tableId, Long userId, boolean isSpectator);
}

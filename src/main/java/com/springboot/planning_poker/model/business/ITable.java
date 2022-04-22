package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.TableUpdate;

import java.util.List;
import java.util.Set;


public interface ITable {
    GameTable addTable(GameTable table, Long id);
    void addGuestToTableAsCreated(TableUpdate tableUpdate);
    void addUserToTable(TableUpdate tableUpdate);
    GameTable getTableById(String id);
    List<Long> getUserJoinTable(String id);
    List<Long> removeUserFromTable(String id, Long userId);
}

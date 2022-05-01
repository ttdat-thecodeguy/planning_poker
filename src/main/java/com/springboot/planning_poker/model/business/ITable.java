package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.GameJoins;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.payload.request.TableUpdate;

import javax.persistence.Tuple;
import java.util.List;


public interface ITable {
    GameTable addTable(GameTable table, Long id );
    void addGuestToTableAsCreated(TableUpdate tableUpdate);
    void addUserToTable(TableUpdate tableUpdate);
    GameTable getTableById(String id);
    GameTable updateTableOwner(Long userId, String tableId) throws Exception;
    // List<UserSocketRequest> removeUserFromTableAndGetList(String id, Long userId);




}

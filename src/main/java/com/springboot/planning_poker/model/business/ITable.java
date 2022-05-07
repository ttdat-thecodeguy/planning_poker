package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.GameJoins;
import com.springboot.planning_poker.model.enity.*;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;

import javax.persistence.Tuple;
import java.util.List;


public interface ITable {
    GameTable addTable(GameTable table, Long id );
    public void updateJoinUserToTable(TableUpdateUser tableUpdate);
    GameTable findTableById(String id);
    GameTable updateTableOwner(Long userId, String tableId) throws Exception;
    public Issue updateTableIssue(String tableId, String issueId, boolean isAdd);
    public void addGuestToTableAsCreated(TableUpdateUser tableUpdate);
}

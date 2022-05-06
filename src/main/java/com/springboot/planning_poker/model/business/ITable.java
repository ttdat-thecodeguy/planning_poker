package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.Issue;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;


public interface ITable {
    GameTable addTable(GameTable table, Long id );
    void updateJoinUserToTable(TableUpdateUser tableUpdate);
    GameTable findTableById(String id);
    GameTable updateTableOwner(Long userId, String tableId) throws Exception;
    Issue findTableAndUpdateTableIssue(String issueId, String tableId, boolean isAdd);
}

package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.Issue;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;


public interface ITable {
    GameTable addTable(GameTable table, Long id );
<<<<<<< Updated upstream
    void updateJoinUserToTable(TableUpdateUser tableUpdate);
    GameTable findTableById(String id);
    GameTable updateTableOwner(Long userId, String tableId) throws Exception;
    Issue findTableAndUpdateTableIssue(String issueId, String tableId, boolean isAdd);
=======
    void addGuestToTableAsCreated(TableUpdateUser tableUpdate);
    void addUserToTable(TableUpdateUser tableUpdate);
    GameTable findTableById(String id);
    GameTable updateTableOwner(Long userId, String tableId) throws Exception;
    public Issue updateTableIssue(String tableId, String issueId, boolean isAdd) throws Exception;
    // List<UserSocketRequest> removeUserFromTableAndGetList(String id, Long userId);




>>>>>>> Stashed changes
}

package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.Issue;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;
import com.springboot.planning_poker.model.responsitory.GameJoinsRepo;
import com.springboot.planning_poker.model.responsitory.IssueRepo;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<<<<<<< Updated upstream
=======
import org.springframework.web.server.ResponseStatusException;
>>>>>>> Stashed changes


@Service
@RequiredArgsConstructor
@Transactional
public class GameTableService implements ITable {
    private final TableRepo tableRepo;
    private final UserRepo userRepo;
    private final IssueRepo issueRepo;
<<<<<<< Updated upstream

=======
    private final GameJoinsRepo gameJoinsRepo;
>>>>>>> Stashed changes
    @Override
    public GameTable addTable(GameTable table, Long userId) {
        if(userId != null){
            User user = userRepo.findById(userId).orElse(null); // TODO cần check lại và quăng exception
            table.setUserOwner(user);
        }
        table.setName(table.getName() == "" ? "Planning Poker Game" : table.getName());
        return tableRepo.save(table);
    }

<<<<<<< Updated upstream


    @Override
    public void updateJoinUserToTable(TableUpdateUser tableUpdate) {
        GameTable table = this.findTableById(tableUpdate.getTableId());
        User user = userRepo.findById(tableUpdate.getUserId()).orElse(null); // TODO cần check lại và quăng exception
        table.addUsersJoin(user);
=======
    @Override
    public void addGuestToTableAsCreated(TableUpdateUser tableUpdate) {
        tableRepo.addUserToTable(tableUpdate.getUserId(), tableUpdate.getTableId());
    }

    @Override
    public void addUserToTable(TableUpdateUser tableUpdate) {
        GameTable gameTable = this.findTableById(tableUpdate.getTableId());
        User user = userRepo.getById(tableUpdate.getUserId());
        gameTable.addUsersJoin(user);
>>>>>>> Stashed changes
    }

    @Override
    public GameTable findTableById(String id) {
<<<<<<< Updated upstream
        return this.findTableById(id);
    }

	@Override
	public GameTable  updateTableOwner(Long userId, String tableId) throws Exception {
        GameTable table = this.findTableById(tableId);
        User user = userRepo.findById(userId).orElse(null); // TODO cần check lại và quăng exception
        table.setUserOwner(user);
		return tableRepo.save(table);
	}

    @Override
    public Issue findTableAndUpdateTableIssue(String tableId, String issueId, boolean isAdd) {
        GameTable table = this.findTableById(tableId);
        Issue issue = issueRepo.findById(issueId).orElse(null);
        if(!isAdd){
            table.setIssueActive(issue);
        } else{
            table.setIssueActive(null);
        }
        tableRepo.save(table);
        return issue;

    }
=======
        GameTable table = tableRepo.findById(id).orElse(null);
        if(table == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Table Not in DB");
        return table;
    }

	@Override
	public GameTable updateTableOwner(Long userId, String tableId) throws Exception {
        GameTable gameTable = this.findTableById(tableId);
        gameTable.setUserOwerId(userId);
		return tableRepo.save(gameTable);
	}

    @Override
    public Issue updateTableIssue(String tableId, String issueId, boolean isAdd) throws Exception {
        GameTable gameTable = this.findTableById(tableId);
        Issue issue = issueRepo.findById(issueId).orElse(null);
        if(!isAdd){
            gameTable.setIssueActive(issue);
        } else{
            gameTable.setIssueActive(null);
        }
        tableRepo.save(gameTable);
        return issue;
    }


>>>>>>> Stashed changes
}

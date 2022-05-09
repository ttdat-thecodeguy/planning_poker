package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.enity.*;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;
import com.springboot.planning_poker.model.responsitory.GameJoinsRepo;
import com.springboot.planning_poker.model.responsitory.IssueRepo;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Tuple;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class GameTableService implements ITable {
    private final TableRepo tableRepo;
    private final UserRepo userRepo;
    private final IssueRepo issueRepo;
    private final GameJoinsRepo gameJoinsRepo;
    @Override
    public GameTable addTable(GameTable table, Long userId) {
        if(userId != null){
            //add owner to joined
            table.setUserOwerId(userId);
        }
        table.setName(table.getName() == "" ? "Planning Poker Game" : table.getName());
        return tableRepo.save(table);
    }

    @Override
    public void addGuestToTableAsCreated(TableUpdateUser tableUpdate) {
        tableRepo.addUserToTable(tableUpdate.getUserId(), tableUpdate.getTableId());
    }



    @Override
    public void updateJoinUserToTable(TableUpdateUser tableUpdate, boolean isSpectator) {
        GameTable gameTable = this.findTableById(tableUpdate.getTableId());
        User user = userRepo.getById(tableUpdate.getUserId());
        gameJoinsRepo.save(new GameJoins(new GameJoinId(gameTable.getId(), user.getId()), null, false, isSpectator));
    }

    @Override
    public GameTable findTableById(String id) {
        GameTable table = tableRepo.findById(id).orElse(null);
        if(table == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Table Not in DB");
        return table;
    }

	@Override
	public GameTable updateTableOwner(Long userId, String tableId) throws Exception {
		GameTable table = this.findTableById(tableId);
		table.setUserOwerId(userId);
		return tableRepo.save(table);
	}

    @Override
    public Issue updateTableIssue(String tableId, String issueId, boolean isAdd) {
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
}

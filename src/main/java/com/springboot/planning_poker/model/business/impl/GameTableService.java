package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.Constants;
import com.springboot.planning_poker.model.definition.StatusCode;
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
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
public class GameTableService implements ITable {
    private final TableRepo tableRepo;
    private final UserRepo userRepo;
    private final IssueRepo issueRepo;
    private final GameJoinsRepo gameJoinsRepo;
    private final IUser userBus;
    @Override
    public GameTable addTable(GameTable table, Long userId) {
        if(userId != null){
            User u = userBus.findUserById(userId);
            table.setUserOwerId(userId);
        }
        table.setName(Objects.equals(table.getName(), "") || table.getName() == null ? Constants.GAME_DEFAULT_NAME : table.getName());
        return tableRepo.save(table);
    }

    @Override
    public void updateJoinUserToTable(TableUpdateUser tableUpdate, boolean isSpectator) {
        GameTable gameTable = this.findTableById(tableUpdate.getTableId());
        User user = userRepo.getById(tableUpdate.getUserId());
        GameJoins gameJoins = GameJoins.builder().id(new GameJoinId(gameTable.getId(), user.getId())).isSpectator(isSpectator).build();
        gameJoinsRepo.save(gameJoins);
    }

    @Override
    public GameTable findTableById(String id) {
        GameTable table = tableRepo.findById(id).orElse(null);
        if(table == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, StatusCode.TABLE_NOT_FOUND);
        return table;
    }

	@Override
	public GameTable updateTableOwner(Long userId, String tableId)  {
		GameTable table = this.findTableById(tableId);
        User u = userBus.findUserById(userId);
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

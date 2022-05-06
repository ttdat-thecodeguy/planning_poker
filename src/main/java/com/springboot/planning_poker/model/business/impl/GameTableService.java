package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.TableUpdate;
import com.springboot.planning_poker.model.responsitory.GameJoinsRepo;
import com.springboot.planning_poker.model.responsitory.IssueRepo;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class GameTableService implements ITable {
    private final TableRepo tableRepo;
    private final UserRepo userRepo;
    private final IssueRepo issueRepo;

    @Override
    public GameTable addTable(GameTable table, Long userId) {
        if(userId != null){
            User user = userRepo.findById(userId).orElse(null); // TODO cần check lại và quăng exception
            table.setUserOwner(user);
        }
        table.setName(table.getName() == "" ? "Planning Poker Game" : table.getName());
        return tableRepo.save(table);
    }



    @Override
    public void updateJoinUserToTable(TableUpdateUser tableUpdate) {
        GameTable table = this.findTableById(tableUpdate.getTableId());
        User user = userRepo.findById(tableUpdate.getUserId()).orElse(null); // TODO cần check lại và quăng exception
        table.addUsersJoin(user);
    }

    @Override
    public GameTable findTableById(String id) {
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
}

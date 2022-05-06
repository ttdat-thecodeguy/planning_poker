package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.Issue;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;
import com.springboot.planning_poker.model.responsitory.GameJoinsRepo;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class GameTableService implements ITable {
    private final TableRepo tableRepo;
    private final UserRepo userRepo;
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
    public void addGuestToTableAsCreated(TableUpdate tableUpdate) {
        tableRepo.addUserToTable(tableUpdate.getUserId(), tableUpdate.getTableId());
    }

    @Override
    public void addUserToTable(TableUpdate tableUpdate) {
        GameTable gameTable = tableRepo.getById(tableUpdate.getTableId());
        User user = userRepo.getById(tableUpdate.getUserId());
        gameTable.addUsersJoin(user);
    }

    @Override
    public GameTable getTableById(String id) {
        return tableRepo.findById(id).orElse(null);
    }

	@Override
	public GameTable updateTableOwner(Long userId, String tableId) throws Exception {
		GameTable table = tableRepo.findById(tableId).orElse(null);
		if(table == null) throw new Exception("Table not found in db");
		table.setUserOwerId(userId);
		return tableRepo.save(table);
	}



//    @Override
//    public List<Tuple> getDetailsOfTable(String id) {
//        return gameJoinsRepo.findAllById_TableId(id);
//
//    }

//    @Override @Transactional
//    public List<UserSocketRequest> removeUserFromTableAndGetList(String id, Long userId) {
//        GameTable gameTable = tableRepo.getById(id);
//        User user = userRepo.getById(userId);
//        return gameTable.removeUsersJoin(user).getListUserSocketRequest();
//    }

}

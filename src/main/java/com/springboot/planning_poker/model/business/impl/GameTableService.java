package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.TableUpdate;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import com.springboot.planning_poker.model.responsitory.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class GameTableService implements ITable {
    private final TableRepo tableRepo;
    private final UserRepo userRepo;
    @Override
    public GameTable addTable(GameTable table, Long userId) {
        if(userId != null){
            //add owner to joined
            table.setUserOwerId(userId);
            table.getJoins().add(new User(userId));
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
    public List<Long> getUserJoinTable(String id) {
        GameTable gameTable = tableRepo.getById(id);
        return gameTable.getListUserId();
    }

    @Override @Transactional
    public List<Long> removeUserFromTableAndGetList(String id, Long userId) {
        GameTable gameTable = tableRepo.getById(id);
        User user = userRepo.getById(userId);
        return gameTable.removeUsersJoin(user).getListUserId();
    }

}

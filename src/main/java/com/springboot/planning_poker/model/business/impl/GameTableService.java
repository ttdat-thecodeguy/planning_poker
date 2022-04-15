package com.springboot.planning_poker.model.business.impl;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.responsitory.TableRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GameTableService implements ITable {
    private final TableRepo tableRepo;
    @Override
    public GameTable addTable(GameTable table) {
        return tableRepo.save(table);
    }
}

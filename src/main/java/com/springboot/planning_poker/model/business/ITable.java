package com.springboot.planning_poker.model.business;

import com.springboot.planning_poker.model.enity.GameTable;
import org.springframework.stereotype.Repository;


public interface ITable {
    GameTable addTable(GameTable table);

}

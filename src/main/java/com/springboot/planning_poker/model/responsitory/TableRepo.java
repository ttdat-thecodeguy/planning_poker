package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.enity.GameTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepo extends JpaRepository<GameTable, String> {
}

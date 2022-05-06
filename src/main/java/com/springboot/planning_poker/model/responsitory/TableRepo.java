package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.enity.GameTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface TableRepo extends JpaRepository<GameTable, String> {
}

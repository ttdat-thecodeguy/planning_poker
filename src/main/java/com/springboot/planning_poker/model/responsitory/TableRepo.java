package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.enity.GameTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TableRepo extends JpaRepository<GameTable, String> {
    @Modifying
    @Transactional
    @Query("UPDATE GameTable table SET table.userOwner.id =:id WHERE table.id =:tableId")
    public void addUserToTable(Long id, String tableId);
}

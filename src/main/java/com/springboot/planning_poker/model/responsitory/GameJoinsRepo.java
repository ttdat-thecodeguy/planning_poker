package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.dto.DeckCountDTO;
import com.springboot.planning_poker.model.enity.GameJoinId;
import com.springboot.planning_poker.model.enity.GameJoins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameJoinsRepo extends JpaRepository<GameJoins, GameJoinId> {


    Optional<GameJoins> findById(GameJoinId id);
    List<GameJoins> findById_TableId(String id);

    @Query("SELECT new com.springboot.planning_poker.model.dto.DeckCountDTO(g.item, COUNT(g.item))" +
            "FROM GameJoins g "+
            "WHERE g.id.tableId = :tableId AND g.isSpectator = false AND g.item IS NOT NULL " +
            "GROUP BY g.item")
    List<DeckCountDTO> countDeckInTable(@Param("tableId") String tableId);
}

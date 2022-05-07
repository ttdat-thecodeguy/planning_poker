package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.enity.GameJoinId;
import com.springboot.planning_poker.model.enity.GameJoins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameJoinsRepo extends JpaRepository<GameJoins, GameJoinId> {


    Optional<GameJoins> findById(GameJoinId id);

    @Query("SELECT g, u.displayName, t.issueActive.id, t.issueActive.name FROM GameJoins g JOIN User u ON g.id.userId = u.id JOIN GameTable t ON g.id.tableId = t.id left join t.issueActive WHERE g.id.tableId = :tableId")
    List<Tuple> findDetailsOfTableById_TableId(@Param("tableId") String tableId);

    @Query("SELECT COUNT(g.item) as count, g.item as item FROM GameJoins g WHERE g.id.tableId = :tableId GROUP BY g.item")
    List<Tuple> countDeckInTable(@Param("tableId") String tableId);
}

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


    @Query("SELECT g, u.displayName FROM GameJoins g JOIN User u ON g.id.userId = u.id WHERE g.id.tableId = :tableId")
    List<Tuple> findAllById_TableId(@Param("tableId") String tableId);
}

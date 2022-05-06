package com.springboot.planning_poker.model.responsitory;

import com.springboot.planning_poker.model.enity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepo extends JpaRepository<Issue, String> {
    @Query("SELECT issue from Issue issue where issue.gameTable.id = :tableId")
    List<Issue> findByTableId(@Param("tableId") String tableId);
}

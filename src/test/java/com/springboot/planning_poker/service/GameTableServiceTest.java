package com.springboot.planning_poker.service;

import com.springboot.planning_poker.constant.TestConstant;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.ITableIssue;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.Constants;
import com.springboot.planning_poker.model.definition.StatusCode;
import com.springboot.planning_poker.model.enity.GameJoins;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.Issue;
import com.springboot.planning_poker.model.enity.User;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
public class GameTableServiceTest {
    @Autowired private ITable tableBus;
    @Autowired private IUser userBus;
    @Autowired private ITableIssue issueBus;
    GameTable gameTableInit;

    @PostConstruct
    public void initData(){
        gameTableInit = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        tableBus.addTable(gameTableInit, null);
    }

    @Test
    public void whenAddTableWithUserOwnerId_returnGameTableHasOwner(){
        User user = userBus.findUserByEmail(TestConstant.EMAIL_TEST);
        GameTable gameTableValid = tableBus.addTable(new GameTable(null,"ABC", Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        assertThat(tableBus.addTable(gameTableValid, user.getId()).getUserOwerId()).isNotNull();
    }

    @Test
    public void whenAddTableWithoutOwnerId_returnGameTable(){
        GameTable gameTableValid = tableBus.addTable(new GameTable(null,"BCD", Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        assertThat(tableBus.addTable(gameTableValid, null)).isInstanceOf(GameTable.class).isNotNull();
    }

    @Test
    public void whenAddTableWithEmptyName_returnGameTableWithNameDefault(){
        GameTable gameTableValid = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        assertThat(tableBus.addTable(gameTableValid, null).getName()).isEqualTo(Constants.GAME_DEFAULT_NAME);
    }
    /*update user join table */
    @Test
    public void whenUpdateJoinUserToTable_shouldReturnGameJoins(){
        User user = userBus.findUserByEmail(TestConstant.EMAIL_TEST);

        assertThat(tableBus.updateJoinUserToTable(TableUpdateUser.builder()
                .tableId(gameTableInit.getId())
                .userId(user.getId()).build(), false)).isInstanceOf(GameJoins.class);
    }
    @Test
    public void whenUpdateJoinUserToTableTableIdNotExists_shouldThrowResourceNotFoundException(){
        User user = userBus.findUserByEmail(TestConstant.EMAIL_TEST);

        assertThatThrownBy(() -> tableBus.updateJoinUserToTable(TableUpdateUser.builder()
                .tableId("")
                .userId(user.getId()).build(), false)).isInstanceOf(ResponseStatusException.class);

    }
    @Test
    public void whenUpdateJoinUserToTableUserIdNotExists_shouldThrowResourceNotFoundException(){
        assertThatThrownBy(() -> tableBus.updateJoinUserToTable(TableUpdateUser.builder()
                .tableId(gameTableInit.getId())
                .userId(10000L).build(), false)).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenUpdateJoinUserToTableTableIdAndUserIdNotExists_shouldReturnGameJoins(){
        assertThatThrownBy(() -> tableBus.updateJoinUserToTable(TableUpdateUser.builder()
                .tableId("")
                .userId(10000L).build(), false)).isInstanceOf(ResponseStatusException.class);
    }
    /*find table id*/
    @Test
    public void whenFindTableByExistsId_returnGameTable(){
        assertThat(tableBus.findTableById(gameTableInit.getId())).isInstanceOf(GameTable.class).isNotNull();
    }

    @Test
    public void whenFindTableByNotExistsId_throwResourceStatusException(){
        assertThatThrownBy(() -> tableBus.findTableById("not_exists_table_id")).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenFindTableByNullId_throwResourceStatusException(){
        assertThatThrownBy(() -> tableBus.findTableById(null)).isInstanceOf(InvalidDataAccessApiUsageException.class);
    }
    @Test
    public void whenFindTableByEmptyId_throwResourceStatusException(){
        assertThatThrownBy(() -> tableBus.findTableById("")).isInstanceOf(ResponseStatusException.class);
    }
    /*update table owner*/
    @Test
    public void whenUpdateTableValid_shouldGameTable() throws Exception {
        User user = userBus.findUserByEmail(TestConstant.EMAIL_TEST);
        assertThat(tableBus.updateTableOwner(user.getId(), gameTableInit.getId())).isInstanceOf(GameTable.class).isNotNull();
    }
    @Test
    public void whenUpdateTableNotExistsTableId_shouldThrowResourceNotFoundExceptionAndHasMessage() throws Exception {
        User user = userBus.findUserByEmail(TestConstant.EMAIL_TEST);
        assertThatThrownBy(() -> tableBus.updateTableOwner(user.getId(), "not_exists_table_id")).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenUpdateTableNotExistsUserId_shouldThrowResourceNotFoundExceptionAndHasMessage() throws Exception {
        assertThatThrownBy(() -> tableBus.updateTableOwner(10000L, gameTableInit.getId())).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenUpdateTableNotExistsUserIdAndNotExistsTableId_shouldThrowResourceNotFoundException() throws Exception {
        assertThatThrownBy(() -> tableBus.updateTableOwner(10000L, "not_exists_table_id")).isInstanceOf(ResponseStatusException.class);
    }
    /*update table issue*/
    @Test
    public void whenUpdateTableIssueValid_returnIssue(){
        //mock data
        Issue issue = new Issue(null,"Issue 01",null, null, null, null);
        issueBus.addIssue(issue, gameTableInit.getId());
        assertThat(tableBus.updateTableIssue(gameTableInit.getId(), issue.getId(), false)).isInstanceOf(Issue.class);
    }
    @Test
    public void whenUpdateTableIssueWithNotExistsTableId_throwResponseStatusException(){
        //mock data
        Issue issue = new Issue(null,"Issue 02",null, null, null, null);
        issueBus.addIssue(issue, gameTableInit.getId());
        assertThatThrownBy(() -> tableBus.updateTableIssue("zzzz", issue.getId(), false)).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenUpdateTableIssueWithNotExistsIssueId_throwResponseStatusException(){
        //mock data
        Issue issue = new Issue("PP-100000","Issue 02",null, null, null, null);
        assertThatThrownBy(() -> tableBus.updateTableIssue(gameTableInit.getId(), issue.getId(), false)).isInstanceOf(ResponseStatusException.class);
    }

}

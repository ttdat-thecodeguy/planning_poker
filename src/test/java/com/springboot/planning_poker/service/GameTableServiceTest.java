package com.springboot.planning_poker.service;

import com.springboot.planning_poker.constant.TestConstant;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.Constants;
import com.springboot.planning_poker.model.definition.StatusCode;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.User;
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
    /*todo update user join table*/

    /*todo find table id*/
    @Test
    public void whenFindTableByExistsId_returnGameTable(){
        assertThat(tableBus.findTableById(gameTableInit.getId())).isInstanceOf(GameTable.class).isNotNull();
    }

    @Test
    public void whenFindTableByNotExistsId_throwResourceStatusException(){
        assertThatThrownBy(() -> tableBus.findTableById("not_exists_id")).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenFindTableByNullId_throwResourceStatusException(){
        assertThatThrownBy(() -> tableBus.findTableById(null)).isInstanceOf(InvalidDataAccessApiUsageException.class);
    }
    @Test
    public void whenFindTableByEmptyId_throwResourceStatusException(){
        assertThatThrownBy(() -> tableBus.findTableById("")).isInstanceOf(ResponseStatusException.class);
    }
    /*
        GameTable updateTableOwner(Long userId, String tableId) throws Exception;
        Issue updateTableIssue(String tableId, String issueId, boolean isAdd);
     */




}

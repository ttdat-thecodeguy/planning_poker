package com.springboot.planning_poker.service;

import com.springboot.planning_poker.constant.TestConstant;
import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.Constants;
import com.springboot.planning_poker.model.definition.RoleEnum;
import com.springboot.planning_poker.model.definition.StatusCode;
import com.springboot.planning_poker.model.dto.DeckCountDTO;
import com.springboot.planning_poker.model.enity.*;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class GameJoinsServiceTest {

    @Autowired private ITable tableBus;
    @Autowired private IGameJoins gameJoinsBus;
    @Autowired private IUser userBus;
    @Autowired private PasswordEncoder encoder;

    GameTable gameTableInit;
    User userInit;

    @PostConstruct
    public void initData(){
        /// mock data
        gameTableInit = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        userInit = userBus.findUserByEmail(TestConstant.EMAIL_TEST);
        tableBus.addTable(gameTableInit, null);
        tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTableInit.getId()).userId(userInit.getId()).build(), false);
    }
    /*find game joins by id*/
    @Test
    public void whenFindGameJoinsById_returnGameJoins(){
        assertThat(gameJoinsBus.findGameJoinsById(gameTableInit.getId(), userInit.getId())).isInstanceOf(GameJoins.class);
    }
    @Test
    public void whenFindGameJoinsById_NotExistsTableId_throwResourceNotFoundException(){
        assertThatThrownBy(() -> gameJoinsBus.findGameJoinsById("", userInit.getId())).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenFindGameJoinsById_NotExistsUserId_throwResourceNotFoundException(){
        assertThatThrownBy(() -> gameJoinsBus.findGameJoinsById(gameTableInit.getId(), 10000000L)).isInstanceOf(ResponseStatusException.class);
    }
    @Test
    public void whenFindGameJoinsById_NotExistsUserIdAndTableId_throwResourceNotFoundException(){
        assertThatThrownBy(() -> gameJoinsBus.findGameJoinsById("", 10000000L)).isInstanceOf(ResponseStatusException.class);
    }
    /*getDetailOfTable*/
    @Test
    public void whenGetDetailsOfTable_shouldReturnListGameJoins() throws Exception {
        /// mock fake data
        //when add
        GameTable gameTable = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        ///add user
        User user = userBus.addUser(User.builder().id(null).displayName("testA").email("test@789.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build());
        tableBus.addTable(gameTable, user.getId());
        ///when user join to table
        GameJoins gameJoinsA = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(user.getId()).build(), false);
        gameJoinsBus.updateGameWhenSelected(gameTable.getId(), user.getId(), "TS");
        assertThat(gameJoinsBus.getDetailOfTable(gameTable.getId()).size()).isEqualTo(1);
    }
    @Test
    public void whenGetDetailsOfTable_NotExistsTable_shouldReturnEmptyList(){
        assertThat(gameJoinsBus.getDetailOfTable("ABCD").size()).isEqualTo(0);
    }
    /*get game result*/
    @Test
    public void whenGetGameResult_shouldReturnListGameResult() throws Exception {
        GameTable gameTable = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        ///add user
        User userA = userBus.addUser(User.builder().id(null).displayName("testA").email("test@kkk.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build());
        User userB = userBus.addUser(User.builder().id(null).displayName("testB").email("test@jjj.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build());
        tableBus.addTable(gameTable, userA.getId());
        ///when user join to table
        GameJoins gameJoinsA = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(userA.getId()).build(), false);
        GameJoins gameJoinsB = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(userB.getId()).build(), false);
        /// when user selected deck
        gameJoinsBus.updateGameWhenSelected(gameTable.getId(), userA.getId(), "TS");
        gameJoinsBus.updateGameWhenSelected(gameTable.getId(), userB.getId(), "CC");
        /// when get game result
        assertThat(gameJoinsBus.getGameResult(gameTable.getId()).size()).isEqualTo(2);
    }
    /*calculate GameResult*/
    @Test
    public void whenCalculateGameResult_shouldReturnDeckDTO() throws Exception {
        GameTable gameTable = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        ///add user
        User userA = userBus.addUser(User.builder().id(null).displayName("testA").email("test@ddddd.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build());
        User userB = userBus.addUser(User.builder().id(null).displayName("testB").email("test@eeeee.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build());
        tableBus.addTable(gameTable, userA.getId());
        ///when user join to table
        GameJoins gameJoinsA = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(userA.getId()).build(), false);
        GameJoins gameJoinsB = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(userB.getId()).build(), false);
        /// when user selected deck
        gameJoinsBus.updateGameWhenSelected(gameTable.getId(), userA.getId(), "TS");
        gameJoinsBus.updateGameWhenSelected(gameTable.getId(), userB.getId(), "CC");
        /// exception
        DeckCountDTO deckCountException = new DeckCountDTO("CC", 1);
        List<DeckCountDTO> lstGameDetails = gameJoinsBus.getGameResult(gameTable.getId());
        assertThat(gameJoinsBus.calculateGameResult(lstGameDetails)).isEqualTo(deckCountException);
    }
    @Test
    public void whenCalculateGameResult_NotUserSelected_shouldReturnNull() throws Exception {
        GameTable gameTable = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        ///add user
        User userA = userBus.addUser(User.builder().id(null).displayName("testA").email("test@rrrrr.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build());
        User userB = userBus.addUser(User.builder().id(null).displayName("testB").email("test@iiiii.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build());
        tableBus.addTable(gameTable, userA.getId());
        ///when user join to table
        GameJoins gameJoinsA = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(userA.getId()).build(), false);
        GameJoins gameJoinsB = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(userB.getId()).build(), false);
        /// exception
        List<DeckCountDTO> lstGameDetails = gameJoinsBus.getGameResult(gameTable.getId());
        assertThat(gameJoinsBus.calculateGameResult(lstGameDetails)).isEqualTo(null);
    }
    /*delete by id*/
    @Test
    public void whenDeleteByIdInvalid_shouldThrowException(){
        assertThatThrownBy(() -> gameJoinsBus.deleteById("", 100000L)).isInstanceOf(Exception.class);
    }
    /*updateGameWhenSelected*/
    @Test
    public void whenUpdateGameWhenSelected_shouldReturnGameJoinsUpdateItem() throws Exception {
        assertThat(gameJoinsBus.updateGameWhenSelected(gameTableInit.getId(), userInit.getId(), "TS").getItem()).isEqualTo("TS");
    }
    @Test
    public void whenUpdateGameWhenSelected_withEmptyItem_shouldThrowException() throws Exception {
        assertThatThrownBy(() -> gameJoinsBus.updateGameWhenSelected(gameTableInit.getId(), userInit.getId(), "")).isInstanceOf(Exception.class).hasMessage(StatusCode.ITEM_INVALID);
    }
    @Test
    public void whenUpdateGameWhenSelected_withNullItem_shouldThrowException() throws Exception {
        assertThatThrownBy(() -> gameJoinsBus.updateGameWhenSelected(gameTableInit.getId(), userInit.getId(), null)).isInstanceOf(Exception.class).hasMessage(StatusCode.ITEM_INVALID);
    }
    /*updateGameWhenUnSelected*/
    @Test
    public void updateGameWhenUnSelected_returnGameJoinsWithItemIsNull(){
        assertThat(gameJoinsBus.updateGameWhenUnSelected(gameTableInit.getId(), userInit.getId()).getItem()).isEqualTo(null);
    }
    /*updateGameWhenSwitchSpectator*/
    @Test
    public void updateGameWhenSwitchSpectatorToFalse_returnGameJoinsWhenSpectatorUpdate(){
        assertThat(gameJoinsBus.updateGameWhenSwitchSpectator(gameTableInit.getId(), userInit.getId(), false).isSpectator()).isEqualTo(false);
    }
    @Test
    public void updateGameWhenSwitchSpectatorToTrue_returnGameJoinsWhenSpectatorUpdate(){
        assertThat(gameJoinsBus.updateGameWhenSwitchSpectator(gameTableInit.getId(), userInit.getId(), true).isSpectator()).isEqualTo(true);
    }
    /*isUserOwnerLeave*/
    @Test
    public void whenUserOwnerLeave_returnTrue() throws Exception {
        /// mock fake data
        //when add
        GameTable gameTable = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        ///add user
        User user = userBus.addUser(User.builder().id(null).displayName("testA").email("test@345.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build());
        tableBus.addTable(gameTable, user.getId());
        ///when user join to table
        GameJoins gameJoins = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(user.getId()).build(), false);
        /// when user selected card
        gameJoinsBus.updateGameWhenSelected(gameTable.getId(), user.getId(), "TS");

        assertThat(gameJoinsBus.isUserOwnerLeave(user.getId(), gameTable.getId())).isTrue();
    }
    @Test
    public void whenUserOwnerNotLeave_returnFalse() throws Exception {
        /// mock fake data
        //when add
        GameTable gameTable = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        ///add user
        User user = userBus.addUser(User.builder().id(null).displayName("testA").email("test@456.com").password(encoder.encode("123")).roles(Set.of(new Role(RoleEnum.ROLE_ADMIN))).build());
        tableBus.addTable(gameTable, user.getId());
        ///when user join to table
        GameJoins gameJoinsA = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(user.getId()).build(), false);
        GameJoins gameJoinsB = tableBus.updateJoinUserToTable(TableUpdateUser.builder().tableId(gameTable.getId()).userId(userInit.getId()).build(), false);
        /// when user selected card
        gameJoinsBus.updateGameWhenSelected(gameTable.getId(), user.getId(), "TS");

        assertThat(gameJoinsBus.isUserOwnerLeave(userInit.getId(), gameTable.getId())).isFalse();
    }
}

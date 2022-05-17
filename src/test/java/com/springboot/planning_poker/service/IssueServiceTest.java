package com.springboot.planning_poker.service;

import com.opencsv.exceptions.CsvValidationException;
import com.springboot.planning_poker.constant.TestConstant;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.ITableIssue;
import com.springboot.planning_poker.model.business.IUser;
import com.springboot.planning_poker.model.definition.Constants;
import com.springboot.planning_poker.model.enity.GameTable;
import com.springboot.planning_poker.model.enity.Issue;
import com.springboot.planning_poker.model.enity.User;
import org.junit.Rule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class IssueServiceTest {

    GameTable gameTableInit;
    Issue issueInit;
    @Autowired private ITable tableBus;
    @Autowired private IUser userBus;
    @Autowired private ITableIssue issueBus;

    @Value("classpath:issues.csv")
    private Resource csvFile;
    @PostConstruct
    public void initData(){
        gameTableInit = tableBus.addTable(new GameTable(null,null, Constants.GAME_DEFAULT_VOTINGS, null, null, false, new HashSet<>(),new HashSet<>(), null), null);
        User user = userBus.findUserByEmail(TestConstant.EMAIL_TEST);
        tableBus.addTable(gameTableInit, user.getId());
        issueInit = issueBus.addIssue(new Issue(null, "Issue 01: Fix",null, null, null, null), gameTableInit.getId());
    }

    /*findById*/
    @Test
    public void whenFindByIdExists_returnIssue(){
        assertThat(issueBus.findById(issueInit.getId())).isInstanceOf(Issue.class);
    }
    @Test
    public void whenFindByIdNotExists_throwResourceStatusException(){
        assertThatThrownBy(() -> issueBus.findById("")).isInstanceOf(ResponseStatusException.class);
    }
    /*findIssueByGameTableId*/
    @Test
    public void whenFindIssueByGameTableIdValid_returnListIssue(){
        assertThat(issueBus.findIssueByGameTableId(gameTableInit.getId()).size()).isEqualTo(1);
    }

    //// inlay hints
    @Test
    public void whenFindIssueBy_NotExistsGameTableId_returnEmptyList(){
       assertThat(issueBus.findIssueByGameTableId("").size()).isEqualTo(0);
    }
    /*addIssue*/
    @Test
    public void whenAddIssue_withExistTableId_returnIssue(){
        assertThat(issueBus.addIssue(new Issue(null, "Issue 02: Create",null, null, null, null), gameTableInit.getId()));
    }
    @Test
    public void whenAddIssue_withNotExistsTableId_throwResourceStatusException(){
        assertThatThrownBy(() -> issueBus.addIssue(new Issue(null, "Issue 02: Create",null, null, null, null), "")).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    public void whenImportIssueFromCSVAndGetListIssue_returnList() throws IOException, CsvValidationException {
        File file = csvFile.getFile();
        assertThat(issueBus.importIssueFromCSVAndGetListIssue(file,gameTableInit.getId(), false).size()).isEqualTo(4);
    }

    @Test
    public void whenImportIssueFromCSVAndGetListIssue_includeHeader_returnList() throws IOException, CsvValidationException {
        File file = csvFile.getFile();
        assertThat(issueBus.importIssueFromCSVAndGetListIssue(file,gameTableInit.getId(), true).size()).isEqualTo(3);
    }
    @Disabled("Disabled test because if i test it on website, the method will be work, but in junit test it will be LazyInitException")
    @Test
    public void whenImportFromUrls_returnList(){
        assertThat(issueBus.importFromUrls(List.of("https://vladmihalcea.com/tutorials/spring"), gameTableInit.getId()).size()).isEqualTo(1);
    }
}

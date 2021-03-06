package com.springboot.planning_poker.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.business.IRole;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.ITableIssue;
import com.springboot.planning_poker.model.dto.GameJoinsDTO;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;
import com.springboot.planning_poker.model.payload.response.socket_message.Message;
import com.springboot.planning_poker.model.payload.response.socket_message.SuccessMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@Slf4j
public class PlayGameController {



    @Autowired private IGameJoins gameJoinsBus;
    @Autowired private ITable tableBus;
    @Autowired private ITableIssue issueBus;
    @MessageMapping("/add-user")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor){
    	log.info("send message : add user id {}", message.getSender());
    	
        headerAccessor.getSessionAttributes().put("id", message.getSender());
        headerAccessor.getSessionAttributes().put("table", message.getTable());

        tableBus.updateJoinUserToTable(new TableUpdateUser(message.getTable(), message.getSender()), message.isSpectator());
        List<GameJoinsDTO> tableDetails = gameJoinsBus.getDetailOfTable(message.getTable());
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(tableDetails);
        message.setContent(json);
        message.setSpectator(message.isSpectator());
        return message;
    }

    @Deprecated
    @MessageMapping("/send-message")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        /// will be code something if it is necessary
        return message;
    }

    @MessageMapping("/selected-card")
    @SendTo("/topic/public")
    public Message selectedCard(@Payload Message message) throws Exception {
    	log.info("message");

        gameJoinsBus.updateGameWhenSelected(message.getTable(),
                                            message.getSender(),
                                            message.getContent().split("-")[2]);
        return message;
    }

    @MessageMapping("/unselected-card")
    @SendTo("/topic/public")
    public Message unselectedCard(@Payload Message message) {
        gameJoinsBus.updateGameWhenUnSelected(message.getTable(),
                                              message.getSender());
        return message;
    }

    @MessageMapping("/activate-spectator")
    @SendTo("/topic/public")
    public Message activateSpectator(@Payload Message message){
        gameJoinsBus.updateGameWhenSwitchSpectator(message.getTable(),
                                                   message.getSender(),
                                         true);
        return message;
    }

    @MessageMapping("/deactivate-spectator")
    @SendTo("/topic/public")
    public Message deactivateSpectator(@Payload Message message){
        gameJoinsBus.updateGameWhenSwitchSpectator(message.getTable(),
                message.getSender(),
                false);
        return message;
    }

    @MessageMapping("/show-card")
    @SendTo("/topic/public")
    public SuccessMessage showCard(@Payload Message message){
        SuccessMessage result = new SuccessMessage(message);
        var resultDetails = gameJoinsBus.getGameResult(message.getTable());
        if (message.getIssue() != null){
            var deckCount = gameJoinsBus.calculateGameResult(resultDetails);
            result.setStoryPoint(deckCount.getItem());
            issueBus.updateResultToIssue(message.getIssue(),deckCount.getItem());
        }
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(resultDetails);
        result.setContent(json);
        return result;
    }

    @MessageMapping("/start-new-vote")
    @SendTo("/topic/public")
    public Message startNewVote(@Payload Message message){
        gameJoinsBus.resetGameJoins(message.getTable());
        return message;
    }

    /***/
    @MessageMapping("/add-issue")
    @SendTo("/topic/public")
    public Message addIssue(@Payload Message message){
        return message;
    }

    @MessageMapping("/selected-issue")
    @SendTo("/topic/public")
    public Message selectedIssue(@Payload Message message){
        return message;
    }

    @MessageMapping("/unselected-issue")
    @SendTo("/topic/public")
    public Message unselectedIssue(@Payload Message message){
        return message;
    }

    @MessageMapping("/import-from-urls")
    @SendTo("/topic/public")
    public Message importFromUrls(@Payload Message message){
        return message;
    }

    @MessageMapping("/import-from-csv")
    @SendTo("/topic/public")
    public Message importFromCSV(@Payload Message message){
        return message;
    }

    @MessageMapping("/delete-issue")
    @SendTo("/topic/public")
    public Message deleteIssue(@Payload Message message){
        return message;
    }
}

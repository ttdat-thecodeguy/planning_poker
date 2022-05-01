package com.springboot.planning_poker.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.payload.request.TableUpdate;
import com.springboot.planning_poker.model.payload.response.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.persistence.Tuple;
import java.util.List;


@Controller @Slf4j
public class PlayGameController {
    @Autowired private IGameJoins gameJoinsBus;
    @Autowired private ITable tableBus;
    @MessageMapping("/add-user")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor){
    	log.info("send message : add user id {}", message.getSender());
    	
        headerAccessor.getSessionAttributes().put("id", message.getSender());
        headerAccessor.getSessionAttributes().put("table", message.getTable());
        tableBus.addUserToTable(new TableUpdate(message.getTable(), message.getSender()));

        List<Tuple> users = gameJoinsBus.getDetailsOfTable(message.getTable());
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(users);
        message.setContent(json);
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
    public Message selectedCard(@Payload Message message) {
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
    public Message showCard(@Payload Message message){
        /// will be code something if it is necessary
        return message;
    }

    @MessageMapping("/start-new-vote")
    @SendTo("/topic/public")
    public Message startNewVote(@Payload Message message){
        /// will be code something if it is necessary
        return message;
    }

    @MessageMapping("/start-new-vote-with-issue")
    @SendTo("/topic/public")
    public Message startNewVoteWithIssue(@Payload Message message){
        /// will be code something if it is necessary
        return message;
    }
}

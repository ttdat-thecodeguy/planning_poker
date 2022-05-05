package com.springboot.planning_poker.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.business.ITableIssue;
import com.springboot.planning_poker.model.payload.request.TableUpdateUser;
import com.springboot.planning_poker.model.payload.response.Message;
import com.springboot.planning_poker.model.payload.response.SuccessMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.persistence.Tuple;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


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
    public SuccessMessage showCard(@Payload Message message){
        SuccessMessage result = new SuccessMessage(message);
        List<Tuple> resultDetails = gameJoinsBus.getGameResult(message.getTable());
        if (message.getIssue() != null){
            /// set story point to issue
            var count = resultDetails.get(0).get(0, Long.class);
            /// đếm thẻ có nhiều lượt vote nhất //nếu các thẻ bằng nhau thì lấy thẻ đầu tiên
            AtomicReference<String> item = new AtomicReference<>(resultDetails.get(0).get(1, String.class));
            resultDetails.forEach(i -> {
                if(i.get(0, Long.class) > count){
                    item.set(i.get(1, String.class ));
                }
            });
            result.setStoryPoint(item.get());
            // update result to issue
            issueBus.updateResultToIssue(message.getIssue(), item.get());
        }
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(resultDetails);
        result.setContent(json);
        return result;
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

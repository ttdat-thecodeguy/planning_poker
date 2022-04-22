package com.springboot.planning_poker.controller;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.message.Message;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@AllArgsConstructor @NoArgsConstructor
//class Deck{
    //int point;
//}

@Controller @Slf4j
public class PlayGameController {

    /*
        0: chưa chọn bài

    */

//    final Map<String,
//    List<Integer>> users_of_table = new HashMap<>();

    @Autowired private ITable table;

    @MessageMapping("/send-message")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("/add-user")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("id", message.getSender());
        List<Long> users = table.getUserJoinTable(message.getTable());
        message.setContent(users.toString());
        return message;
    }
}

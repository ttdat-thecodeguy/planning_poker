package com.springboot.planning_poker.model.payload.response.socket_message;

import com.springboot.planning_poker.model.payload.response.socket_message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SuccessMessage extends Message {

    public SuccessMessage(Message message){
        super(message.getSender(), message.getTable(), message.getContent(),  message.isSpectator() , message.getMessageType(), message.getIssue());
    }

    @Getter @Setter
    private String storyPoint;
}

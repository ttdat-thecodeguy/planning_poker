package com.springboot.planning_poker.model.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SuccessMessage extends Message{

    public SuccessMessage(Message message){
        super(message.getSender(), message.getTable(), message.getContent(), message.getMessageType(), message.getIssue());
    }

    @Getter @Setter
    private String storyPoint;
}

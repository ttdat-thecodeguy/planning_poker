package com.springboot.planning_poker.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Message {

    private Long sender;
    private String table;
    private String content;
    private MessageType messageType;

    public enum MessageType {
        JOIN,
        SELECTED,
        CHANGE_CARD,
        ACTIVE_SPECTATOR,
        DEACTIVATE_SPECTATOR,
        LEAVE
    }
}

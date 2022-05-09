package com.springboot.planning_poker.model.payload.response.socket_message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor @NoArgsConstructor @Slf4j @Data
public class LeaveMessage extends Message{
    private boolean isUserOwnerLeave;
}

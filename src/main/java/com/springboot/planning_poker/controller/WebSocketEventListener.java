package com.springboot.planning_poker.controller;

import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.message.Message;
import com.springboot.planning_poker.model.payload.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component @Slf4j
public class WebSocketEventListener {
    @Autowired private SimpMessageSendingOperations messageSendingOperations;
    @Autowired private ITable tableBus;
    @EventListener public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("new connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String user = (String) headerAccessor.getSessionAttributes().get("id");
        String table = (String) headerAccessor.getSessionAttributes().get("table");
        if (user != null) {
            Long id = Long.valueOf(user);
            log.info ("user {} cancel connection", user);
            Message message = new Message();
            message.setMessageType(Message.MessageType.LEAVE);
            message.setSender(Long.valueOf(user));
            message.setContent(tableBus.removeUserFromTable(table, id).toString());
            messageSendingOperations.convertAndSend("/topic/public", message);
        }
    }
}

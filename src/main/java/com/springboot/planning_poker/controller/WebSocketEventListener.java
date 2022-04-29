package com.springboot.planning_poker.controller;

import com.springboot.planning_poker.model.business.IGameJoins;
import com.springboot.planning_poker.model.business.ITable;
import com.springboot.planning_poker.model.payload.response.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component @Slf4j
public class WebSocketEventListener {
    @Autowired private SimpMessageSendingOperations messageSendingOperations;
    @Autowired private IGameJoins gameJoins;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info ("new connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("close connection");

        Long user = (Long) headerAccessor.getSessionAttributes().get("id");
        String tableId = (String) headerAccessor.getSessionAttributes().get("table");
        if (user != null) {
            log.info ("user {} cancel connection", user);
            //delete table
            gameJoins.deleteById(tableId, user);
            Message message = new Message();
            message.setMessageType(Message.MessageType.LEAVE);
            message.setSender(Long.valueOf(user));
            message.setTable(tableId);
            messageSendingOperations.convertAndSend("/topic/public", message);
        }
    }


}

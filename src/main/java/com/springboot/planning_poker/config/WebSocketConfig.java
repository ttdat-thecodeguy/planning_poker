package com.springboot.planning_poker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //prefix này sẽ đón những message từ phía server
        // vd: stompClient.subscribe('/topic/public')
        config.enableSimpleBroker("/topic");
        //prefix sẽ nhận được message từ client
        // vd: stompClient.send('/app/add')
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // endpoint khi connect
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*").withSockJS()
                //được kích hoạt cho các trình duyệt không hỗ trợ SockJS, nó sẽ kích hoạt thư viện này
                .setClientLibraryUrl("/webjars/sockjs-client/1.1.2/sockjs.js")
                .setSessionCookieNeeded(false);
    }
}

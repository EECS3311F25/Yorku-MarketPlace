package eecs3311.group.p.Marketplace.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Topic prefix for sending data TO clients
        config.enableSimpleBroker("/topic");
        // Application prefix for data FROM clients
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Matches "new SockJS('/ws-chat')" in your chat.html
        registry.addEndpoint("/ws-chat").withSockJS();
    }
}
package fr.esigelec.ping.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Bean
    public WebSocketService webSocketService() {
        return new WebSocketService();
    }

    @Bean
    public WebSocketService2 webSocketService2() {
        return new WebSocketService2();
    }

    @Bean
    public NotificationWebSocketService notificationWebSocketService() {
        return new NotificationWebSocketService();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketService(), "/wss")
                .setAllowedOrigins("*");

        registry.addHandler(webSocketService2(), "/wss2")
                .setAllowedOrigins("*");

        registry.addHandler(notificationWebSocketService(), "/notifications")
                .setAllowedOrigins("*");
    }
}

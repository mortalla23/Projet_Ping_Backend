package fr.esigelec.ping.websocket;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Route pour le premier WebSocket
        registry.addHandler(webSocketService(), "/ws")
                .setAllowedOrigins("*");

        // Route pour le deuxième WebSocket
        registry.addHandler(webSocketService2(), "/ws2")
                .setAllowedOrigins("*");
    }
}

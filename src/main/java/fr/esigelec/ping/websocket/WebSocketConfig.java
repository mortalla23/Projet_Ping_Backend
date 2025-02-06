package fr.esigelec.ping.websocket;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
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
        // Route pour le premier WebSocket
        System.out.println("Message de débogage ici");
        registry.addHandler(webSocketService(), "/ws")
        .setAllowedOrigins("http://localhost:3000") // Remplacez par les origines autorisées
                       ; 


        // Route pour le deuxième WebSocket
        System.out.println("Message2 de débogage ici");
        registry.addHandler(webSocketService2(), "/ws2")
                .setAllowedOrigins("*")
                ; 
    }
}

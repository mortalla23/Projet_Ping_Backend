package fr.esigelec.ping.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationWebSocketService extends TextWebSocketHandler {
    // ✅ Stocke les sessions WebSocket par utilisateur (recipientId)
    private final ConcurrentHashMap<Integer, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Récupère l'utilisateur à partir des paramètres de la connexion WebSocket
        Integer userId = (Integer) session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.put(userId, session);
            System.out.println("✅ Connexion WebSocket établie pour l'utilisateur " + userId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        Integer userId = (Integer) session.getAttributes().get("userId");
        if (userId != null) {
            userSessions.remove(userId);
            System.out.println("❌ WebSocket fermé pour l'utilisateur " + userId);
        }
    }

    // 🔥 Envoie une notification à UN SEUL utilisateur
    public void sendNotification(int recipientId, String notificationMessage) {
        WebSocketSession session = userSessions.get(recipientId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(notificationMessage));
                System.out.println("📩 Notification envoyée à " + recipientId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("⚠ L'utilisateur " + recipientId + " n'est pas connecté");
        }
    }
}

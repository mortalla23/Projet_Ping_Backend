package fr.esigelec.ping.websocket;

import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service  // ✅ Indique à Spring de gérer cette classe comme un Bean
public class WebSocketService extends TextWebSocketHandler {

    // Map : conversationId → liste des sessions des utilisateurs
    private static final Map<Integer, List<WebSocketSession>> conversationSessions = new HashMap<>();

    // 🔔 Lorsqu'un utilisateur se connecte
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Nouvelle connexion : " + session.getRemoteAddress());
    }

    // 📩 Lorsqu'un message est reçu (pour s'abonner à une conversation)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Message reçu : " + payload);

        // Format attendu : { "action": "subscribe", "conversationId": 1 }
        if (payload.contains("subscribe")) {
            int conversationId = extractConversationId(payload);

            // Ajout de la session à la conversation
            conversationSessions
                .computeIfAbsent(conversationId, k -> new CopyOnWriteArrayList<>())
                .add(session);

            session.sendMessage(new TextMessage("{\"message\": \"Inscription réussie à la conversation " + conversationId + "\"}"));
        }
    }

    // 🔊 Diffuser un vrai message à tous les abonnés d'une conversation
    public void broadcastMessage(int conversationId, String content,  int userId) {
        List<WebSocketSession> sessions = conversationSessions.get(conversationId);

        if (sessions != null) {
            sessions.removeIf(session -> !session.isOpen());  // Nettoyer les connexions fermées

            for (WebSocketSession session : sessions) {
                try {
                    // 🔥 Construire le vrai message
                    String messageJson = new Document()
                        .append("type", "message")
                        .append("conversationId", conversationId)
                        .append("content", content)
                        .append("senderId", userId)
                        .append("createdAt", new Date().toString())
                        .toJson();

                    session.sendMessage(new TextMessage(messageJson));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 🔍 Extraction de l'ID de la conversation depuis le JSON
    private int extractConversationId(String payload) {
        // Méthode simplifiée : il vaut mieux utiliser un parseur JSON
        return Integer.parseInt(payload.replaceAll("\\D+", ""));
    }

}

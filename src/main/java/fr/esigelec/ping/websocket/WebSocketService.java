package fr.esigelec.ping.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketService extends TextWebSocketHandler {

    private static final Map<Integer, List<WebSocketSession>> sessionsByConversation = new HashMap<>();

    // Lorsque la connexion WebSocket est établie
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Nouvelle connexion WebSocket : " + session.getRemoteAddress());
    }

    // Lorsque la connexion WebSocket est fermée
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        System.out.println("Connexion WebSocket fermée : " + status.getReason());
        for (List<WebSocketSession> sessions : sessionsByConversation.values()) {
            sessions.remove(session);
        }
    }

    // Lorsque un message est reçu
    @Override
    protected void handleTextMessage(WebSocketSession session, org.springframework.web.socket.TextMessage message) throws Exception {
    	try {
    	    String messageText = message.getPayload();
    	    // Parse du message JSON
    	    Map<String, Object> data = new ObjectMapper().readValue(messageText, Map.class);
    	    
    	    Integer conversationId = (Integer) data.get("conversation_id");
    	    if (conversationId == null) {
    	        session.sendMessage(new org.springframework.web.socket.TextMessage("{\"error\": \"conversation_id manquant\"}"));
    	        return; 
    	    }

    	    // Ajout de la session à la conversation
    	    sessionsByConversation
    	        .computeIfAbsent(conversationId, k -> new CopyOnWriteArrayList<>())
    	        .add(session);

    	    session.sendMessage(new org.springframework.web.socket.TextMessage("{\"message\": \"Inscription à la conversation réussie.\"}"));
    	} catch (Exception e) {
    	    e.printStackTrace();
    	    session.sendMessage(new org.springframework.web.socket.TextMessage("{\"error\": \"Erreur dans le traitement du message.\"}"));
    	}

    }


    // Diffuser un message à tous les participants de la conversation
    public static void broadcastMessage(int conversationId, String message, MongoCollection<Document> messages) {
        List<WebSocketSession> sessions = sessionsByConversation.get(conversationId);
        if (sessions != null) {
            sessions.removeIf(session -> !session.isOpen());
            for (WebSocketSession session : sessions) {
                try {
                    String messageWithDate = new Document()
                        .append("content", message)
                        .append("createdAt", new Date().toString())
                        .toJson();
                    session.sendMessage(new org.springframework.web.socket.TextMessage(messageWithDate));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Enregistrer le message dans MongoDB
        Document messageDocument = new Document()
            .append("conversation_id", conversationId)
            .append("content", message)
            .append("created_at", new Date())
            .append("is_read", false);
        messages.insertOne(messageDocument);
    }
}

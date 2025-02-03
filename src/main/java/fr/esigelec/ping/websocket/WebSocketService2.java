package fr.esigelec.ping.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.esigelec.ping.model.Participant;
import fr.esigelec.ping.service.ParticipantService;

import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class WebSocketService2 extends TextWebSocketHandler {

    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private ParticipantService participantService;
    // Map : userId → liste des sessions
    private static final Map<Integer, List<WebSocketSession>> sessionsByUser = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 🔔 Lorsqu'une connexion est établie
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Nouvelle connexion WebSocket : " + session.getRemoteAddress());
    }

    // 🔌 Lorsqu'une connexion est fermée
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        System.out.println("Connexion WebSocket fermée : " + status.getReason());

        // Supprimer la session de toutes les conversations
        for (List<WebSocketSession> sessions : sessionsByUser.values()) {
            sessions.remove(session);
        }
    }

    // 📩 Lorsqu'un message est reçu
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            String payload = message.getPayload();
            System.out.println("Message reçu : " + payload);

            // Parse le message JSON
            Map<String, Object> data = objectMapper.readValue(payload, Map.class);
           // Récupérer le userId du message
    String userIdStr = (String) data.get("userId");

    // Vérification si userId est manquant ou non convertible en entier
    if (userIdStr == null || userIdStr.isEmpty()) {
        session.sendMessage(new TextMessage("{\"error\": \"userId manquant ou vide\"}"));
        return;
    }
            // Convertir userId en entier
        Integer userId = Integer.parseInt(userIdStr);

            // Ajouter la session à la conversation
            sessionsByUser
                    .computeIfAbsent(userId, k -> new CopyOnWriteArrayList<>())
                    .add(session);

            session.sendMessage(new TextMessage("{\"message\": \"Inscription réussie à l'utilisateur " + userId + "\"}"));

        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.sendMessage(new TextMessage("{\"error\": \"Erreur dans le traitement du message.\"}"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

   // Diffuser un message à tous les participants d'une conversation
    public void broadcastMessage(int conversationId, int senderId, String messageContent) {
        List<Integer> participants = getParticipantsForConversation(conversationId);

        if (participants.isEmpty()) {
            System.err.println("Aucun participant trouvé pour la conversation " + conversationId);
            return;
        }

        for (Integer userId : participants) {
            List<WebSocketSession> sessions = sessionsByUser.get(userId);

            if (sessions != null) {
                sessions.removeIf(session -> !session.isOpen()); // Supprimer les sessions fermées

                for (WebSocketSession session : sessions) {
                    try {

                        JSONObject messageJson = new JSONObject();
                        messageJson.put("content", messageContent);
                        messageJson.put("type", "newMessage");
                        messageJson.put("senderId", senderId);
                        messageJson.put("conversationId", conversationId);
                        messageJson.put("createdAt", new Date().toString());
                
                       
                        // Envoyer le message
                        session.sendMessage(new org.springframework.web.socket.TextMessage(messageJson.toString()));
                        System.out.println("Message envoyé à l'utilisateur " + userId + ": " + messageJson);
                    } catch (IOException e) {
                        System.err.println("Erreur lors de l'envoi du message : " + e.getMessage());
                    }
                }
            }
        }
    }

    // Diffuser une notification à tous les participants d'une conversation
    public void broadcastNotification(int conversationId, int senderId, String message) {
        String senderName = getSenderName(senderId);
        if (senderName == null) {
            System.err.println("Nom de l'expéditeur introuvable pour senderId : " + senderId);
            return;
        }

        List<Integer> participants = getParticipantsForConversation(conversationId);

        for (Integer userId : participants) {
            List<WebSocketSession> sessions = sessionsByUser.get(userId);

            if (sessions != null) {
                sessions.removeIf(session -> !session.isOpen()); // Supprimer les sessions fermées

                for (WebSocketSession session : sessions) {
                    try {
                        // Construire la notification JSON
                        String notification = String.format(
                                "{ \"type\": \"notification\", \"conversationId\": %d, \"sender\": \"%s\", \"content\": \"%s\", \"createdAt\": \"%s\" }",
                                conversationId, senderName, message, new Date().toString());

                        // Envoyer la notification
                        session.sendMessage(new org.springframework.web.socket.TextMessage(notification));
                        System.out.println("Notification envoyée à l'utilisateur " + userId + ": " + notification);
                    } catch (IOException e) {
                        System.err.println("Erreur lors de l'envoi de la notification : " + e.getMessage());
                    }
                }
            }
        }
    }

   public List<Integer> getParticipantsForConversation(int conversationId) {
    try {
       List <Participant> response=participantService.getParticipantsByConversationId(conversationId);
        
            
            System.out.println("Nb de participants : "+response.size());
            List<Integer> participants = new ArrayList<>();

            for (int i = 0; i < response.size(); i++) {
               participants.add(response.get(i).getUserId()); // Remplace "user_id" par la clé correcte
            }

            return participants;
       
    } catch (Exception e) {
        System.err.println("Erreur lors de l'appel API : " + e.getMessage());
    }
    return Collections.emptyList();
}


    // Récupérer le nom de l'expéditeur via une API
    private String getSenderName(int senderId) {
        String url = "http://localhost:5000/api/users/" + senderId;
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return response != null ? (String) response.get("username") : null;
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération du nom de l'expéditeur : " + e.getMessage());
            return null;
        }
    }

   
}

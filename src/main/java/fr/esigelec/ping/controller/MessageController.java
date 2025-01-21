package fr.esigelec.ping.controller;


import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.esigelec.ping.model.Message;
import fr.esigelec.ping.model.MessageRequest;

import fr.esigelec.ping.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // ➕ Endpoint pour ajouter un message et mettre à jour last_message_id
    @PostMapping("/add")
    public ResponseEntity<?> addMessageToConversation(
            @RequestParam int conversationId,
            @RequestParam int userId,
            @RequestParam String content) {
        try {
            // 🔄 Appel du service pour ajouter le message
            Message message = messageService.addMessage(conversationId, userId, content);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            // ⚠️ Retourne une erreur si la conversation n'existe pas
            return ResponseEntity.badRequest().body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"message\": \"Erreur lors de l'ajout du message.\"}");
        }
    }


     // ✅ Endpoint pour marquer un message comme lu via POST (plus flexible)
@PostMapping("/mark_as_read")
public ResponseEntity<?> markMessageAsRead(@RequestParam int messageId) {
    try {
        if (messageId <= 0) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "L'ID du message est invalide."));
        }

        messageService.markMessageAsRead(messageId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Le message a été marqué comme lu."));

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(Collections.singletonMap("message", "Erreur lors de la mise à jour du message."));
    }
}





    // 🔎 Endpoint pour récupérer les messages associés à une conversation
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<?> getParticipantsByConversationId(@PathVariable("conversationId") int conversationId) {
        try {
            List<Message> participants = messageService.getMessages(conversationId);

            if (participants.isEmpty()) {
                return ResponseEntity.status(404).body("{\"message\": \"Aucun message trouvé pour cette conversation.\"}");
            }

            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"message\": \"Erreur lors de la récupération des messages.\"}");
        }
    }

    // 🔍 Endpoint pour récupérer un message par son ID
    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable int messageId) {
        try {
            Message message = messageService.getMessageById(messageId);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("{\"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"message\": \"Erreur lors de la récupération du message.\"}");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        try {
            messageService.deleteMessage(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Message supprimé avec succès."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "Erreur lors de la suppression du message."));
        }
    }

}

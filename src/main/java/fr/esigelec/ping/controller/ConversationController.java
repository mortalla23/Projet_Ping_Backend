package fr.esigelec.ping.controller;

import fr.esigelec.ping.service.ConversationService;
import fr.esigelec.ping.model.Conversation;
import fr.esigelec.ping.model.ConversationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/create")
    public ResponseEntity<?> createConversation(@RequestParam String userId, @RequestParam boolean isPublic) {
        try {
            // Crée la conversation
            Conversation conversation = new Conversation();
            conversation.setCreatedAt(new Date());
            conversation.setPublic(isPublic);
            conversation.setLastMessageId(0);

            // Sauvegarde la conversation dans la base de données
            Conversation savedConversation = conversationService.createConversation(conversation, userId);
            System.out.println(savedConversation);
            // Assurez-vous que la réponse est bien en JSON
            return ResponseEntity.ok(savedConversation); // La réponse devrait être un JSON

        } catch (Exception e) {
            // En cas d'erreur, retournez un message d'erreur structuré
            return ResponseEntity.status(500).body("{\"message\": \"Erreur lors de la création de la conversation.\"}");
        }
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<?> getConversation(@PathVariable int conversationId) {
        try {
            return ResponseEntity.ok(conversationService.getConversation(conversationId));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Conversation non trouvée.");
        }
    }
}

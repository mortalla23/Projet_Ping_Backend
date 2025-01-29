package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.Conversation;
import fr.esigelec.ping.service.ConversationService;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

   
    @Autowired
    private ConversationService conversationService;

    // 🟢 Création d'une conversation avec ajout automatique du participant
    @PostMapping("/add")
    public ResponseEntity<?> createConversation(@RequestParam boolean isPublic, 
                                                @RequestParam int senderId, 
                                                @RequestParam int receiverId) {
        try {
            Conversation newConversation = conversationService.createConversation(isPublic, senderId, receiverId);
            return ResponseEntity.ok(newConversation);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"message\": \"Erreur lors de la création de la conversation : " + e.getMessage() + "\"}");
        }
    }


    // 🔍 Endpoint : Récupérer les conversations d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Conversation>> getUserConversations(@PathVariable int userId) {
        List<Conversation> conversations = conversationService.getConversationsByUserIds(userId);
        if (conversations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(conversations);
    }
    
     // 🔍 Endpoint : Récupérer toutes les conversations
     @GetMapping("/all")
     public ResponseEntity<List<Conversation>> getAllConversations() {
         List<Conversation> conversations = conversationService.getAllConversations();
         return ResponseEntity.ok(conversations);
     }

     @GetMapping("/check")
     public ResponseEntity<?> checkConversation(@RequestParam int senderId, @RequestParam int receiverId) {
         boolean exists = conversationService.existsByParticipants(senderId, receiverId);
         return ResponseEntity.ok(Collections.singletonMap("exists", exists));
     }

    
}

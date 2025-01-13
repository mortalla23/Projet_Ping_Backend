package fr.esigelec.ping.controller;


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

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest message) {
        try {
            // Appel à la logique d'envoi du message
        	 return ResponseEntity.ok("Message envoyé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi du message.");
        }
    }


    @GetMapping("/{conversationId}")
    public ResponseEntity<?> getMessages(@PathVariable int conversationId) {
        try {
            return ResponseEntity.ok(messageService.getMessages(conversationId));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Aucun message trouvé.");
        }
    }
}

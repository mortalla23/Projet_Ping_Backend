package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.Participant;
import fr.esigelec.ping.service.ConversationService;
import fr.esigelec.ping.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @Autowired  // ✅ Injection du ConversationService
    private ConversationService conversationService;


    // 🔎 Récupérer les participants d'une conversation
    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<?> getParticipantsByConversationId(@PathVariable("conversationId") int conversationId) {
        try {
            List<Participant> participants = participantService.getParticipantsByConversationId(conversationId);

            if (participants.isEmpty()) {
                return ResponseEntity.status(404).body("{\"message\": \"Aucun participant trouvé pour cette conversation.\"}");
            }

            return ResponseEntity.ok(participants);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("{\"message\": \"Erreur lors de la récupération des participants.\"}");
        }
    }

    // ➕ Ajouter un participant à une conversation
    @PostMapping("/add")
    public ResponseEntity<?> addParticipantToConversation(@RequestParam int conversationId, @RequestParam int userId) {
        try {
            // ✅ Vérifie si la conversation existe
            if (!conversationService.existsById(conversationId)) {
                return ResponseEntity.badRequest().body("{\"message\": \"La conversation avec l'ID " + conversationId + " n'existe pas.\"}");
            }
    
            // ✅ Vérifie si l'utilisateur est déjà participant
            boolean alreadyParticipant = participantService.existsByConversationIdAndUserId(conversationId, userId);
            if (alreadyParticipant) {
                return ResponseEntity.badRequest().body("{\"message\": \"L'utilisateur " + userId + " est déjà participant de la conversation.\"}");
            }
    
            // ➕ Ajoute le participant
            Participant participant = participantService.addParticipant(conversationId, userId);
            return ResponseEntity.ok(participant);
    
        } catch (Exception e) {
            e.printStackTrace();  // 🔍 Affiche l'erreur complète dans les logs
            return ResponseEntity.status(500).body("{\"message\": \"Erreur lors de l'ajout du participant : " + e.getMessage() + "\"}");
        }
    }
}

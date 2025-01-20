package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Conversation;
import fr.esigelec.ping.model.Participant;
import fr.esigelec.ping.repository.ConversationRepository;
import fr.esigelec.ping.repository.ParticipantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ParticipantService participantService;  // ➕ Injection du ParticipantService

     @Autowired
    private ParticipantRepository participantRepository;

    
   // 🔧 Création d'une conversation avec ajout du participant
    public Conversation createConversation(boolean isPublic, int userId) {
        int conversationId = generateUniqueConversationId();

        // ➡️ Créer la conversation
        Conversation conversation = new Conversation();
        conversation.setId(conversationId);
        conversation.setIsPublic(isPublic);
        conversation.setCreatedAt(new Date());
        conversation.setLastMessageId(0);

        // ➡️ Sauvegarder la conversation
        Conversation savedConversation = conversationRepository.save(conversation);

        // ➡️ Ajouter le participant via ParticipantService
        participantService.addParticipant(conversationId, userId);

        return savedConversation;
    }

    // ✅ Vérifie si une conversation existe par son ID
    public boolean existsById(int conversationId) {
        return conversationRepository.existsById(conversationId);
    }

    // 🔄 Génération d'un ID unique pour la conversation
    private int generateUniqueConversationId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (conversationRepository.existsById(id));
        return id;
    }

    // 🔍 Récupérer toutes les conversations d'un utilisateur
    public List<Conversation> getUserConversations(int userId) {
        // 🔎 Récupérer les participations de l'utilisateur
        List<Participant> participants = participantRepository.findByUserId(userId);

        // 🔗 Extraire les conversation_id
        List<Integer> conversationIds = participants.stream()
                .map(Participant::getConversationId)
                .collect(Collectors.toList());

        // 📦 Récupérer les conversations correspondantes
        return conversationRepository.findByIdIn(conversationIds);
    }

    // 🔍 Récupérer toutes les conversations
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

}

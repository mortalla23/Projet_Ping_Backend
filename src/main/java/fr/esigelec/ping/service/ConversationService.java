package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Conversation;
import fr.esigelec.ping.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    // Méthode pour créer une conversation
    // Méthode pour créer une conversation
    public Conversation createConversation(Conversation conversation, String userId) {
        conversation.setId(UUID.randomUUID().toString()); // Générer un ID unique
        conversation.setCreatedAt(new Date());
        conversation.setPublic(true);  // Exemple pour public
        conversation.setLastMessageId(0);

        return conversationRepository.save(conversation);
    }


    // Méthode pour récupérer une conversation par ID
    public Conversation getConversation(int conversationId) {
        return conversationRepository.findById(conversationId);
    }
}


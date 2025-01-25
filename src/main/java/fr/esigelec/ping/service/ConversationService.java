package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Conversation;
import fr.esigelec.ping.model.Participant;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.repository.ConversationRepository;
import fr.esigelec.ping.repository.ParticipantRepository;
import fr.esigelec.ping.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

     @Autowired
     private UserRepository userRepository;  // Pour récupérer les usernames
    
   // 🔧 Création d'une conversation avec ajout du participant
     public Conversation createConversation(boolean isPublic, int senderId, int receiverId) {
    	    int conversationId = generateUniqueConversationId();
    	    
    	 // Récupérer les usernames associés aux userIds AVANT de sauvegarder la conversation
            List<Integer> userIds = Arrays.asList(senderId, receiverId);
            List<String> usernames = Arrays.asList(receiverId, senderId).stream()
                    .map(userId -> userRepository.findById(userId).map(User::getUsername).orElse("Utilisateur inconnu"))
                    .collect(Collectors.toList());
            
    	    Conversation conversation = new Conversation();
    	    conversation.setId(conversationId);
    	    conversation.setIsPublic(isPublic);
    	    conversation.setCreatedAt(new Date());
    	    conversation.setLastMessageId(0);
    	    conversation.setUserIds(Arrays.asList(senderId, receiverId));
    	    conversation.setUsernames(usernames);
    	    
    	    Conversation savedConversation = conversationRepository.save(conversation);
    	    
    	    

           ;

    	    // Ajouter les deux participants (expéditeur et destinataire)
    	    participantService.addParticipant(conversationId, senderId);
    	    participantService.addParticipant(conversationId, receiverId);

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
    /*
    public List<Conversation> getConversationsByUserId(int userId) {
        // 🔎 Récupérer les participations de l'utilisateur
        List<Participant> participants = participantRepository.findByUserId(userId);

        // 🔗 Extraire les conversation_id
        List<Integer> conversationIds = participants.stream()
                .map(Participant::getConversationId)
                .collect(Collectors.toList());

        // 📦 Récupérer les conversations correspondantes
        return conversationRepository.findByIdIn(conversationIds);
    }
*/
    public List<Conversation> getConversationsByUserIds(int userId) {
        return conversationRepository.findByUserIdsContaining(userId);
    }
    /*
    public List<Conversation> getConversationsByUserIds(int userId) {
        return conversationRepository.findBySenderIdOrReceiverId(userId, userId);
    }
*/
    // 🔍 Récupérer toutes les conversations
    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }
    
 // Vérifie si une conversation existe entre deux utilisateurs
    public boolean existsByParticipants(int senderId, int receiverId) {
        return conversationRepository.findByUserIds(senderId, receiverId).isPresent();
    }

}

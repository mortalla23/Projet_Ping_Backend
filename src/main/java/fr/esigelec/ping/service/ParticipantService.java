package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Participant;
import fr.esigelec.ping.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;
    

    // 🔧 Création d'un participant
    public Participant addParticipant(int conversationId, int userId) {
        int participantId = generateUniqueParticipantId();

        Participant participant = new Participant();
        participant.setId(participantId);
        participant.setConversationId(conversationId);
        participant.setUserId(userId);
        participant.setJoinedAt(new Date());

        return participantRepository.save(participant);
    }

    // 🔎 Récupérer tous les participants d'une conversation
    public List<Participant> getParticipantsByConversationId(int conversationId) {
        return participantRepository.findByConversationId(conversationId);
    }

    // 🔍 Vérifie si un utilisateur est déjà participant d'une conversation
    public boolean existsByConversationIdAndUserId(int conversationId, int userId) {
        return participantRepository.existsByConversationIdAndUserId(conversationId, userId);
    }

    // 🔄 Génération d'un ID unique pour le participant
    private int generateUniqueParticipantId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (participantRepository.existsById(id));
        return id;
    }
}

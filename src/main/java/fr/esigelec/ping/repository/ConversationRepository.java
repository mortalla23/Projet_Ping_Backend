package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Conversation;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {

    // Rechercher une conversation par son ID
    Conversation findById(int id);

    // Vérifier si une conversation existe en fonction de son ID
    boolean existsById(int id);

    // Récupérer toutes les conversations
    List<Conversation> findAll();
    

}

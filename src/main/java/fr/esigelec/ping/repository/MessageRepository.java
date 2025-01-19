package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    // Récupérer tous les messages d'une conversation
    List<Message> findByConversationId(int conversationId);

    // Vérifier si un message existe en fonction de son ID
    boolean existsById(int id);

    // Récupérer un message spécifique par son ID
    Message findById(int id);
}

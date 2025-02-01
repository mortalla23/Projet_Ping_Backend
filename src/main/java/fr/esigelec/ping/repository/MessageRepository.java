package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends MongoRepository<Message, Integer> {

    // 🔎 Recherche d'un message par ID métier
    @Query("{ 'id': ?0 }")
    Optional<Message> findById(int id);

    // ✅ Vérifie si une conversation existe par son id
    @Query(value = "{ 'id': ?0 }", exists = true)
    boolean existsById(int id);
    
    // 🔍 Récupère tous les messages d'une conversation
    @Query("{ 'conversation_id': ?0 }")
    List<Message> findByConversationId(int conversationId);

    // 🔎 Récupère les messages non lus d'une conversation
    @Query("{ 'conversation_id': ?0, 'is_read': false }")
    List<Message> findUnreadMessagesByConversationId(int conversationId);
}

package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, Integer> {

    // 🔎 Recherche d'une conversation par l'id métier
    @Query("{ 'id': ?0 }")
    Optional<Conversation> findById(int id);
    
    @Query("{ 'user_ids': ?0 }")
    List<Conversation> findByUserId(int userId);

    // ✅ Vérifie si une conversation existe par son id
    @Query(value = "{ 'id': ?0 }", exists = true)
    boolean existsById(int id);

    // 🔍 Récupérer plusieurs conversations par leurs IDs
    List<Conversation> findByIdIn(List<Integer> ids);
    
    @Query("{ 'userIds': { $all: [?0, ?1] } }")
    Optional<Conversation> findByUserIds(int senderId, int receiverId);
    
    List<Conversation> findByUserIdsContaining(int userId);


}


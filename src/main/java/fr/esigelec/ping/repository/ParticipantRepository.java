package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Participant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends MongoRepository<Participant, Integer> {

    // 🔎 Recherche par ID métier
    @Query("{ 'id': ?0 }")
    Optional<Participant> findById(int id);

    // 🔍 Récupérer les participations d'un utilisateur
    List<Participant> findByUserId(int userId);

   
    // ✅ Vérifie si une conversation existe par son id
    @Query(value = "{ 'id': ?0 }", exists = true)
    boolean existsById(int id);

    // 🔎 Récupère les participants d'une conversation
    @Query("{ 'conversation_id': ?0 }")
    List<Participant> findByConversationId(int conversationId);

    // ✅ Vérifie si un utilisateur participe déjà à une conversation
    @Query(value = "{ 'conversation_id': ?0, 'user_id': ?1 }", exists = true)
    boolean existsByConversationIdAndUserId(int conversationId, int userId);
}

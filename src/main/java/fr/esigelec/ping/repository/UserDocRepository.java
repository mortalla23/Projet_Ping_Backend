package fr.esigelec.ping.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.esigelec.ping.model.UserDocument;

public interface UserDocRepository extends MongoRepository<UserDocument, Integer> {
   
    @Query("{ 'user_id': ?0 }")
    List<UserDocument> findByUserId(int userId);

    @Query("{ 'user_id': ?0, 'document_type': ?1 }")
    List<UserDocument> findByUserIdAndDocumentType(int userId, String documentType);

    /**
     * Vérifie si un document existe par son ID (en utilisant le champ 'id' explicite).
     */
    @Query(value = "{ 'id': ?0 }", exists = true)
    boolean existsById(String id);

    /**
     * Supprime un document par son ID (en utilisant le champ 'id' explicite).
     */
    @Query(value = "{ 'id': ?0 }", delete = true)
    void deleteById(int id);

    Optional<UserDocument> findByDocumentId(int documentId);
}

package fr.esigelec.ping.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.esigelec.ping.model.HistoriqueEducation;

public interface HistoriqueEducationRepository extends MongoRepository<HistoriqueEducation, Integer> {

    // Trouver l'historique éducatif par user_id
    @Query("{ 'id': ?0 }")
    HistoriqueEducation findById(int id);

    // Vérifier si un historique éducatif existe par son ID
    @Query(value = "{ 'id': ?0 }", exists = true)
    boolean existsById(Integer id);

    // Supprimer un historique éducatif par son ID
    @Query(value = "{ 'id': ?0 }", delete = true)
    void deleteById(Integer id);
}

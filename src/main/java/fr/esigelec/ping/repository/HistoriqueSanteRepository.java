package fr.esigelec.ping.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import fr.esigelec.ping.model.HistoriqueSante;

public interface HistoriqueSanteRepository extends MongoRepository<HistoriqueSante, Integer> {

    // Trouver l'historique santé par user_id
   @Query("{ 'id': ?0 }")
   HistoriqueSante findById(int id);

    // Vérifier si un historique de santé existe par son ID
    @Query(value = "{ 'id': ?0 }", exists = true)
    boolean existsById(Integer id);

    // Supprimer un historique de santé par son ID
    @Query(value = "{ 'id': ?0 }", delete = true)
    void deleteById(Integer id);
}

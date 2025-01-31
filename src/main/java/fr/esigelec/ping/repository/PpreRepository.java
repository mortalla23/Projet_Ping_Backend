package fr.esigelec.ping.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.esigelec.ping.model.Ppre;

public interface PpreRepository extends MongoRepository<Ppre, Integer> {

    // Trouver l'anamnèse par user_id
    @Query("{ 'id': ?0 }")
    Ppre findById(int id);

    // Vérifier si une anamnèse existe par son ID
    @Query(value = "{ 'id': ?0 }", exists = true)
    boolean existsById(Integer id);

    // Supprimer une anamnèse par son ID
    @Query(value = "{ 'id': ?0 }", delete = true)
    void deleteById(Integer id);
}

package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Amenagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@Repository
public interface AmenagementRepository extends MongoRepository<Amenagement, Integer> {

    // Méthode pour récupérer les aménagements par utilisateur
    @Query(value = "{'user_id': ?0}")
    List<Amenagement> findByUserId(int userId);
    

    // Méthode pour récupérer les aménagements par prescripteur
    @Query(value = "{'id_prescripteur': ?0}")
    List<Amenagement> findByIdPrescripteur(int idPrescripteur);
}

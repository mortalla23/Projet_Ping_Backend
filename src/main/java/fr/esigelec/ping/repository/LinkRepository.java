package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Link;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Map;

public interface LinkRepository extends MongoRepository<Link, String> {

    /**
     * Récupère uniquement les IDs des étudiants liés à un enseignant donné.
     */
	@Query(value = "{ 'linkerId': ?0 }", fields = "{ 'linkedTo': 1, '_id': 0 }")
	List<Map<String, String>> findLinkedToByLinkerId(String teacherId);

	
    /**
     * Récupère tous les liens où l'enseignant est le linkerId.
     */
    @Query("{ 'linkerId': ?0 }")
    List<Link> findLinksByTeacherId(String teacherId);

    /**
     * Récupère tous les liens où un étudiant est lié.
     */
    @Query("{ 'linkedTo': ?0 }")
    List<Link> findByLinkedTo(String studentId);

    /**
     * Vérifie si un lien existe entre un enseignant et un étudiant.
     */
    boolean existsByLinkerIdAndLinkedTo(String linkerId, String linkedTo);
    boolean existsByLinkedToAndLinkerId(String linkedTo, String linkerId);

    
}

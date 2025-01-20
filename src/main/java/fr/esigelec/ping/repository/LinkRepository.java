package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Link;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Map;

public interface LinkRepository extends MongoRepository<Link, Integer> {

    /**
     * Récupère uniquement les IDs des étudiants liés à un enseignant donné.
     */
	@Query(value = "{ 'linkerId': ?0 }", fields = "{ 'linkedTo': 1, '_id': 0 }")
	List<Map<String, Integer>> findLinkedToByLinkerId(int teacherId);

	
    /**
     * Récupère tous les liens où l'enseignant est le linkerId.
     */
    @Query("{ 'linkerId': ?0 }")
    List<Link> findLinksByTeacherId(int teacherId);

     /**
     * Récupère l'intervenant par id.
     */
    @Query("{ 'id': ?0 }")
    List<Link> findById(int id);

    /**
     * Récupère tous les liens où un étudiant est lié.
     */
    @Query("{ 'linkedTo': ?0 }")
    List<Link> findByLinkedTo(int studentId);

    // ✅ Vérifie si une conversation existe par son id
    @Query(value = "{ 'id': ?0 }", exists = true)
    boolean existsById(int id);

    /**
     * Vérifie si un lien existe entre un enseignant et un étudiant.
     */
    //@Query("{ 'linkedTo': ?0 , 'linkerId': ?1 }")
    boolean existsByLinkerIdAndLinkedTo(int linkerId, int linkedTo);
    boolean existsByLinkedToAndLinkerId(int linkedTo, int linkerId);

    
}

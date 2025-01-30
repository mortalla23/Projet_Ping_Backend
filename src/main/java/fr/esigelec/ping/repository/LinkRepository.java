package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.LinkValidation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface LinkRepository extends MongoRepository<Link, String> {

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

    /**
     * Vérifie si un lien existe par son id.
     */
    @Query(value = "{ 'id': ?0 }", exists = true)
    boolean existsById(int id);

    /**
     * Vérifie si un lien existe entre un enseignant et un étudiant.
     */
    boolean existsByLinkerIdAndLinkedTo(int linkerId, int linkedTo);
    boolean existsByLinkedToAndLinkerId(int linkedTo, int linkerId);

    
 // Récupère tous les liens d'un enseignant avec un rôle spécifique
    @Query("{ 'linkerId': ?0, 'role': 'TEACHER' }")
    List<Link> findLinksByTeacherIdAndRole(int teacherId);

 // Méthode pour trouver les liens validés par linkerId
    List<Link> findByLinkerIdAndValidate(int linkerId, String validate);
    
 // Récupérer les liens validés pour un orthophoniste
    @Query("{ 'linkerId': ?0, 'role': 'ORTHOPHONISTE', 'validate': 'VALIDATED' }")
    List<Link> findValidatedLinksByOrthophonist(int linkerId);

    // Récupérer les liens validés pour un enseignant
    @Query("{ 'linkerId': ?0, 'role': 'TEACHER', 'validate': 'VALIDATED' }")
    List<Link> findValidatedLinksByTeacher(int linkerId);

    
    /**
     * Récupère tous les liens validés pour un enseignant donné.
     */
    //@Query("{ 'linkerId': ?0, 'validate': ?1 }")
   // List<Link> findLinksByTeacherIdAndValidate(int teacherId, LinkValidation validate);
    
    @Query("SELECT u FROM User u JOIN Link l ON u.id = l.linkedTo WHERE l.linkerId = :orthoId AND l.validate = 'VALIDATED'")
    List<User> findValidatedPatientsByOrthoId(int orthoId);
   
 //   @Query("{ 'linkerId': ?0, 'validate': 'VALIDATED' }")
  //  List<Link> findValidatedLinksByLinkerId(int linkerId);
    
    @Query("{ 'linkedTo': { $in: ?0 } }")
    List<Link> findByLinkedToIn(List<Integer> patientIds);

 //   @Query("{ 'linkerId': ?0, 'validate': 'VALIDATED', 'role': ?1 }")
   // List<Link> findValidatedLinksByLinkerIdAndRole(int linkerId, String role);

    @Query("{ 'linkedTo': { $in: ?0 } }")
    List<Link> findAllByLinkedToIn(List<Integer> patientIds);
    @Query("{ 'linkerId': ?0, 'validate': 'VALIDATED' }")
    List<Link> findLinksByTeacherIdAndValidate(int linkerId, LinkValidation validate);

    @Query("{ 'linkerId': ?0, 'role': ?1, 'validate': 'VALIDATED' }")
    List<Link> findLinksByTeacherIdAndRoleAndValidate(int linkerId, String role, LinkValidation validate);


   

        // Récupérer les liens validés pour un linker ID
        @Query("SELECT l FROM Link l WHERE l.linkerId = :linkerId AND l.validate = 'VALIDATED'")
        List<Link> findValidatedLinksByLinkerId(@Param("linkerId") int linkerId);

        // Récupérer les liens validés pour un linker ID avec un rôle spécifique
        @Query("SELECT l FROM Link l WHERE l.linkerId = :linkerId AND l.role = :role AND l.validate = 'VALIDATED'")
        List<Link> findValidatedLinksByLinkerIdAndRole(@Param("linkerId") int linkerId, @Param("role") String role);
    }

    
    


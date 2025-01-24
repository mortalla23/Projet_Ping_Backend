package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.PAP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PAPRepository extends MongoRepository<PAP, Integer> {

    // Récupérer tous les PAP pour un user_id donné
    @Query("{ 'user_id': ?0 }")
    List<PAP> findByUserId(int userId);

    

    // Récupérer les PAP avec des objectifs à court terme spécifiques
    @Query("{ 'short_term_goals': ?0 }")
    List<PAP> findByShortTermGoals(String shortTermGoal);

    
}

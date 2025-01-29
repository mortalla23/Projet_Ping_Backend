package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;
import fr.esigelec.ping.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

    // 🔍 Rechercher un utilisateur par son nom d'utilisateur
    Optional<User> findByUsername(String username);

    // 🔍 Rechercher un utilisateur par email
    Optional<User> findByEmail(String email);

    // 🔍 Vérifier si un utilisateur existe avec un email donné
    boolean existsByEmail(String email);

    // 🔍 Recherche progressive des utilisateurs par username
    @Query("{ 'username': { $regex: '^?0', $options: 'i' } }")
    List<User> findByUsernameStartingWith(String prefix);

     // Custom query to search for users by username or email
     @Query("{ '$or': [ { 'username': { '$regex': ?0, '$options': 'i' } }, { 'email': { '$regex': ?1, '$options': 'i' } } ] }")
     List<User> findByUsernameContainingOrEmailContaining(String username, String email);

    @Query("{ 'id': { $in: ?0 } }")
    List<User> findAllByIds(List<Integer> ids);
    
    
        
        @Query("{ 'id': { $in: ?0 } }")
        List<User> findAllById(List<String> ids);


    
}

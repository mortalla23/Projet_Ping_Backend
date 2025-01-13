package fr.esigelec.ping.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import fr.esigelec.ping.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);
    Optional<User> findById(String id);
    List<User> findByRoleIn(List<String> roles);
    Optional<User> findByIdAndRole(String id, String role);
    List<User> findAllById(List<String> ids);
   

    @Query("{ '_id': { $in: ?0 } }")
    List<User> findAllByIds(List<ObjectId> ids);
    Optional<User> findById(ObjectId id);

    // Custom query to search for users by username or email
    @Query("{ '$or': [ { 'username': { '$regex': ?0, '$options': 'i' } }, { 'email': { '$regex': ?1, '$options': 'i' } } ] }")
    List<User> findByUsernameContainingOrEmailContaining(String username, String email);
}

package fr.esigelec.ping.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.esigelec.ping.model.UserDocument;

public interface UserDocRepository extends MongoRepository<UserDocument, String> {
    List<UserDocument> findByUserId(String userId);
    List<UserDocument> findByUserIdAndDocumentType(String userId, String documentType);
}

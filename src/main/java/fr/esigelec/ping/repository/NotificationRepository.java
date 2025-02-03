package fr.esigelec.ping.repository;

import fr.esigelec.ping.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByRecipientIdAndIsReadFalse(int recipientId);
}

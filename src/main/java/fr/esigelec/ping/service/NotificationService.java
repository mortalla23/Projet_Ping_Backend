package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Notification;
import fr.esigelec.ping.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // ✅ Créer une notification et l'enregistrer en base
    public void createNotification(int recipientId, String message) {
        Notification notification = new Notification(recipientId, message);
        notificationRepository.save(notification);
        System.out.println("🔔 Notification créée pour l'utilisateur " + recipientId + " : " + message);
    }

    // ✅ Récupérer les notifications non lues
    public List<Notification> getUnreadNotifications(int recipientId) {
        return notificationRepository.findByRecipientIdAndIsReadFalse(recipientId);
    }

    // ✅ Marquer une notification comme lue
    public void markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
}

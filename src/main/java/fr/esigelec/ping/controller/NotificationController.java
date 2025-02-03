package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.Notification;
import fr.esigelec.ping.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // ✅ Récupérer les notifications non lues d'un utilisateur
    @GetMapping("/{recipientId}")
    public List<Notification> getUnreadNotifications(@PathVariable int recipientId) {
        return notificationService.getUnreadNotifications(recipientId);
    }

    // ✅ Marquer une notification comme lue
    @PutMapping("/mark-as-read/{notificationId}")
    public void markNotificationAsRead(@PathVariable String notificationId) {
        notificationService.markAsRead(notificationId);
    }
}

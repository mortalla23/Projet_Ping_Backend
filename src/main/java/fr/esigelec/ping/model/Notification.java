package fr.esigelec.ping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "notifications") // Spécifie que c'est une collection MongoDB
public class Notification {

    @Id
    private String id;

    @Field(name = "recipient_id") // Stocké en `recipient_id` dans MongoDB
    private int recipientId;

    @Field(name = "message") // Stocké en `message` dans MongoDB
    private String message;

    @Field(name = "created_at") // Stocké en `created_at` dans MongoDB
    private LocalDateTime createdAt;

    @Field(name = "is_read") // Stocké en `is_read` dans MongoDB
    private boolean isRead;

    public Notification() {}

    public Notification(int recipientId, String message) {
        this.recipientId = recipientId;
        this.message = message;
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }

    // Getters et Setters
    public String getId() { return id; }
    public int getRecipientId() { return recipientId; }
    public void setRecipientId(int recipientId) { this.recipientId = recipientId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}

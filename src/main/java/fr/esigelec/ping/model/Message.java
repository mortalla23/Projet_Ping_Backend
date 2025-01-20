package fr.esigelec.ping.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "messages")  // Associe ce modèle à la collection "messages"
public class Message {

    @Field("id")  // Mappé au champ "id" de MongoDB
    private int id;

    @Field("conversation_id")  // Mappé au champ "conversation_id"
    private int conversationId;

    @Field("sender_id")  // Mappé au champ "sender_id"
    private int senderId;

    @Field("content")  // Mappé au champ "content"
    private String content;

    @Field("created_at")  // Mappé au champ "created_at"
    private Date createdAt;

    @Field("is_read")  // Mappé au champ "is_read"
    private boolean isRead;

    @Field("error_message")  // Mappé au champ "error_message"
    private String errorMessage;

    // 🔧 Constructeur par défaut
    public Message() {}

    // 🔧 Constructeur avec paramètres
    public Message(int id, int conversationId, int senderId, String content, Date createdAt, boolean isRead, String errorMessage) {
        this.id = id;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.content = content;
        this.createdAt = createdAt;
        this.isRead = isRead;
        this.errorMessage = errorMessage;
    }

    // 🔑 Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

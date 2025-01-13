package fr.esigelec.ping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "conversations")
public class Conversation {

    @Id
    private String id;
    private boolean isPublic;
    private Date createdAt;
    private int lastMessageId;

    // Constructeurs, getters et setters
    public Conversation() {}

    public Conversation(String id, boolean isPublic, Date createdAt, int lastMessageId) {
        this.id = id;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.lastMessageId = lastMessageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(int lastMessageId) {
        this.lastMessageId = lastMessageId;
    }
}

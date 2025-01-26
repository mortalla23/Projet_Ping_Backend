package fr.esigelec.ping.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "conversations")  // Associe ce modèle à la collection "conversations" de MongoDB
public class Conversation {

    @Field("id")  // Mappé au champ "id" de MongoDB
    private int id;

    @Field("is_public")  // Mappé au champ "is_public" de MongoDB
    private boolean isPublic;

    @Field("created_at")  // Mappé au champ "created_at" de MongoDB
    private Date createdAt;

    @Field("last_message_id")  // Mappé au champ "last_message_id" de MongoDB
    private int lastMessageId;
    
    @Field("user_ids")
    private List<Integer> userIds;
    
    @Field("usernames")  // Mappé aux noms des utilisateurs
    private List<String> usernames;
    // 🔧 Constructeur par défaut
    public Conversation() {}

    // 🔧 Constructeur avec paramètres
    public Conversation(int id, boolean isPublic, Date createdAt, int lastMessageId, List<Integer> userIds, List<String> usernames) {
        this.id = id;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.lastMessageId = lastMessageId;
        this.userIds = userIds;
        this.usernames = usernames;
    }
    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
    // 🔑 Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
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
    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}

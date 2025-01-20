package fr.esigelec.ping.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "participants")  // Associe ce modèle à la collection "participants"
public class Participant {

    @Field("id")  // Mappé au champ "id" de MongoDB
    private int id;

    @Field("conversation_id")  // Mappé au champ "conversation_id" de MongoDB
    private int conversationId;

    @Field("user_id")  // Mappé au champ "user_id" de MongoDB
    private int userId;

    @Field("joined_at")  // Mappé au champ "joined_at" de MongoDB
    private Date joinedAt;

    // 🔧 Constructeur par défaut
    public Participant() {}

    // 🔧 Constructeur avec paramètres
    public Participant(int id, int conversationId, int userId, Date joinedAt) {
        this.id = id;
        this.conversationId = conversationId;
        this.userId = userId;
        this.joinedAt = joinedAt;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Date joinedAt) {
        this.joinedAt = joinedAt;
    }
}

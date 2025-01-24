package fr.esigelec.ping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.esigelec.ping.model.enums.AmenagementValidation;
import lombok.Data;

import java.util.Date;

@Data
@Document(collection = "amenagements")  // Associe ce modèle à la collection "conversations" de MongoDB
public class Amenagement {

    
    @Field("id")  // Mappé au champ "id" de MongoDB
    private int id;

    @Field("user_id")  // Mappé au champ "is_public" de MongoDB
    private int userId;

    @Field("id_prescripteur")  // Mappé au champ "is_public" de MongoDB
    private int idPrescripteur;

    @Field("type")  // Mappé au champ "last_message_id" de MongoDB
    private String type;

    @Field("motif")  // Mappé au champ "last_message_id" de MongoDB
    private String motif;

    @Field("objectifs")  // Mappé au champ "last_message_id" de MongoDB
    private String objectifs;

    @Field("status")  // Mappé au champ "last_message_id" de MongoDB
    private AmenagementValidation status;

    @Field("date_debut")  // Mappé au champ "created_at" de MongoDB
    private Date dateDebut;

    @Field("date_fin")  // Mappé au champ "created_at" de MongoDB
    private Date dateFin;

    @Field("created_at")  // Mappé au champ "created_at" de MongoDB
    private Date createdAt;

    @Field("updated_at")  // Mappé au champ "created_at" de MongoDB
    private Date updatedAt;

    
    // 🔧 Constructeur par défaut
    public Amenagement() {}

    // 🔧 Constructeur avec paramètres
    public Amenagement(int idPrescripteur, int userId, String type, String motif, String objectifs, Date dateDebut, Date dateFin ) {
        this.idPrescripteur = idPrescripteur;
        this.userId = userId;
        this.type = type;
        this.motif = motif;
        this.objectifs = objectifs;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // 🔑 Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    public AmenagementValidation getStatus() {
        return this.status;
    }

    public void setStatus(AmenagementValidation status) {
        this.status = status;
    }

    
}

package fr.esigelec.ping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_documents")
public class UserDocument {
    
    
    @Field("id") // Mappé au champ "id" de la base de données
    private int id;

    @Field("user_id") // Mappé au champ "user_id" de la base de données
    private int userId;

    @Field("document_id") // Mappé au champ "document_name" de la base de données
    private int documentId;

    @Field("document_name") // Mappé au champ "document_name" de la base de données
    private String documentName;

    @Field("document_type") // Mappé au champ "document_type" de la base de données
    private String documentType; // Type du document (exemple : "PAP", "PAI", "PRE")

    @Field("is_public") // Mappé au champ "is_public" de la base de données
    private Boolean isPublic; // Indique si le document est public ou non

    @Field("created_at") // Mappé au champ "created_at" de la base de données
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


    @Field("updated_at") // Mappé au champ "updated_at" de la base de données
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;


    @Field("updated_by") // Mappé au champ "updated_by" de la base de données
    private String updatedBy;



    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDocumentId() {
        return this.documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Boolean isIsPublic() {
        return this.isPublic;
    }

    public Boolean getIsPublic() {
        return this.isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}

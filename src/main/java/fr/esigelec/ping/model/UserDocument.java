
package fr.esigelec.ping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_documents")
public class UserDocument {
    
    
    @Field("id") // Mappé au champ "id" de la base de données
    private int id;

    @Field("user_id") // Mappé au champ "user_id" de la base de données
    private int userId;

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
}

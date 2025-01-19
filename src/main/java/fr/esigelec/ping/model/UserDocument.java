
package fr.esigelec.ping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_documents")
public class UserDocument {
    
    @Id
    private String id; 

    private String userId; 
    private String documentName; 

    private String documentType; // Type du document (exemple : "PAP", "PAI", "PRE")

    private Boolean isPublic; // Indique si le document est public ou non

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  createdAt; 

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt; 

    private String updatedBy; 
}

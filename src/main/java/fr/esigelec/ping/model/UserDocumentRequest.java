package fr.esigelec.ping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocumentRequest {
    private int userId;
    private String documentName;
    private String documentType;
    private Boolean isPublic;
    private String updatedBy;
}

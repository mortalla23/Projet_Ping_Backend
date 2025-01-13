package fr.esigelec.ping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "link")
public class Link {

    @Id
    private String id; // Identifiant unique pour MongoDB

    private String linkerId; // ID de l'enseignant
    private String linkedTo; // ID de l'élève
    private String validate; // Statut de la relation
 // Constructeur par défaut (requis par certaines bibliothèques comme MongoDB)
    public Link() {}

    // Constructeur personnalisé
    public Link(String linkerId, String linkedTo, String validate) {
        this.linkerId = linkerId;
        this.linkedTo = linkedTo;
        this.validate = validate;
    }
    // Getters et setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkerId() {
        return linkerId;
    }

    public void setLinkerId(String linkerId) {
        this.linkerId = linkerId;
    }

    public String getLinkedTo() {
        return linkedTo;
    }

    public void setLinkedTo(String linkedTo) {
        this.linkedTo = linkedTo;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }
}

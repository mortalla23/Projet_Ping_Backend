package fr.esigelec.ping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.esigelec.ping.model.enums.LinkValidation;

@Document(collection = "link") // Représente la collection MongoDB "link"
public class Link {

    @Id // ID unique généré automatiquement par MongoDB
    private String id;

    @Field("linker_id") // Champ mappé à "linker_id" dans MongoDB
    private int linkerId; // ID de l'utilisateur (orthophoniste ou enseignant)

    @Field("linked_to") // Champ mappé à "linked_to" dans MongoDB
    private int linkedTo; // ID du patient

    @Field("validate") // Champ mappé à "validate" dans MongoDB
    private LinkValidation validate; // Statut du lien (VALIDATED, ONGOING, etc.)

    @Field("role") // Nouveau champ pour indiquer le rôle
    private String role; // Rôle du lien : "ORTHOPHONISTE" ou "ENSEIGNANT"

    // Constructeur par défaut requis par MongoDB
    public Link() {}

    // Constructeur avec paramètres
    public Link(int linkerId, int linkedTo, LinkValidation validate, String role) {
        this.linkerId = linkerId;
        this.linkedTo = linkedTo;
        this.validate = validate;
        this.role = role != null ? role : "UNKNOWN";
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLinkerId() {
        return linkerId;
    }

    public void setLinkerId(int linkerId) {
        this.linkerId = linkerId;
    }

    public int getLinkedTo() {
        return linkedTo;
    }

    public void setLinkedTo(int linkedTo) {
        this.linkedTo = linkedTo;
    }

    public LinkValidation getValidate() {
        return validate;
    }

    public void setValidate(LinkValidation validate) {
        this.validate = validate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id='" + id + '\'' +
                ", linkerId=" + linkerId +
                ", linkedTo=" + linkedTo +
                ", validate=" + validate +
                ", role='" + role + '\'' +
                '}';
    }
}

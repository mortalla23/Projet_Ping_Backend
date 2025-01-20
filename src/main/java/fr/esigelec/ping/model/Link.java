package fr.esigelec.ping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.model.enums.Role;

@Document(collection = "link")
public class Link {

    @Field("id")  // Mappé au champ "id" de MongoDB
    private int id; // 
    @Field("linker_id")  // Mappé au champ "linker_id" de MongoDB
    private int linkerId; // ID de l'enseignant
    @Field("linked_to")  // Mappé au champ "linked_to" de MongoDB
    private int linkedTo; // ID de l'élève
    @Field("validate")  // Mappé au champ "validate" de MongoDB
    private LinkValidation validate;
 // Constructeur par défaut (requis par certaines bibliothèques comme MongoDB)
    public Link() {}

    // Constructeur personnalisé
    public Link(int linkerId, int linkedTo, LinkValidation validate) {
        this.linkerId = linkerId;
        this.linkedTo = linkedTo;
        this.validate = validate;
    }
    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}

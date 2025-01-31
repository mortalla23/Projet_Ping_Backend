package fr.esigelec.ping.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ppre")
public class Ppre {
 
    
    @Field("id") // Mappé au champ "id" de la base de données
    private int id;

    @Field("objectifs_educatifs") // Mappé au champ "objectifs_educatifs" de la base de données
    private String objectifsEducatifs;

    
    @Field("mesures_pedagogiques") // Mappé au champ "mesures_pedagogiques" de la base de données
    private String mesuresPedagogiques;

    @Field("evaluation") // Mappé au champ "evaluation" de la base de données
    private String evaluation;

    @Field("suivi") // Mappé au champ "suivi" de la base de données
    private String suivi;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObjectifsEducatifs() {
        return this.objectifsEducatifs;
    }

    public void setObjectifsEducatifs(String objectifsEducatifs) {
        this.objectifsEducatifs = objectifsEducatifs;
    }

    public String getMesuresPedagogiques() {
        return this.mesuresPedagogiques;
    }

    public void setMesuresPedagogiques(String mesuresPedagogiques) {
        this.mesuresPedagogiques = mesuresPedagogiques;
    }

    public String getEvaluation() {
        return this.evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getSuivi() {
        return this.suivi;
    }

    public void setSuivi(String suivi) {
        this.suivi = suivi;
    }

}

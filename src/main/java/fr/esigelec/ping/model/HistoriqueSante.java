package fr.esigelec.ping.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "historique_sante") // Associe ce modèle à la collection "historique_sante" de MongoDB
public class HistoriqueSante {

    public HistoriqueSante(int id, String antecedentsMedicaux, String antecedentsFamiliaux, String suiviEnCours, String acuiteVisuelle, String acuiteAuditive, String autresBilans, String developpementAlimentation, String difficulteAliments, boolean respireBoucheOuverte, boolean fatigueMatin, boolean sucePouceTetine, boolean ronflement, String activitesExtrascolaires, String tempsEcran) {
        this.id = id;
        this.antecedentsMedicaux = antecedentsMedicaux;
        this.antecedentsFamiliaux = antecedentsFamiliaux;
        this.suiviEnCours = suiviEnCours;
        this.acuiteVisuelle = acuiteVisuelle;
        this.acuiteAuditive = acuiteAuditive;
        this.autresBilans = autresBilans;
        this.developpementAlimentation = developpementAlimentation;
        this.difficulteAliments = difficulteAliments;
        this.respireBoucheOuverte = respireBoucheOuverte;
        this.fatigueMatin = fatigueMatin;
        this.sucePouceTetine = sucePouceTetine;
        this.ronflement = ronflement;
        this.activitesExtrascolaires = activitesExtrascolaires;
        this.tempsEcran = tempsEcran;
    }

    @Field("id")
    private int id;

    @Field("antecedents_medicaux")
    private String antecedentsMedicaux;

    @Field("antecedents_familiaux")
    private String antecedentsFamiliaux;

    @Field("suivi_en_cours")
    private String suiviEnCours;

    @Field("acuite_visuelle")
    private String acuiteVisuelle;

    @Field("acuite_auditive")
    private String acuiteAuditive;

    @Field("autres_bilans")
    private String autresBilans;

    @Field("developpement_alimentation")
    private String developpementAlimentation;

    @Field("difficulte_aliments")
    private String difficulteAliments;

    @Field("respire_bouche_ouverte")
    private boolean respireBoucheOuverte;

    @Field("fatigue_matin")
    private boolean fatigueMatin;

    @Field("suce_pouce_tetine")
    private boolean sucePouceTetine;

    @Field("ronflement")
    private boolean ronflement;

    @Field("activites_extrascolaires")
    private String activitesExtrascolaires;

    @Field("temps_ecran")
    private String tempsEcran;

    // Constructeur par défaut
    public HistoriqueSante() {}

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAntecedentsMedicaux() {
        return this.antecedentsMedicaux;
    }

    public void setAntecedentsMedicaux(String antecedentsMedicaux) {
        this.antecedentsMedicaux = antecedentsMedicaux;
    }

    public String getAntecedentsFamiliaux() {
        return this.antecedentsFamiliaux;
    }

    public void setAntecedentsFamiliaux(String antecedentsFamiliaux) {
        this.antecedentsFamiliaux = antecedentsFamiliaux;
    }

    public String getSuiviEnCours() {
        return this.suiviEnCours;
    }

    public void setSuiviEnCours(String suiviEnCours) {
        this.suiviEnCours = suiviEnCours;
    }

    public String getAcuiteVisuelle() {
        return this.acuiteVisuelle;
    }

    public void setAcuiteVisuelle(String acuiteVisuelle) {
        this.acuiteVisuelle = acuiteVisuelle;
    }

    public String getAcuiteAuditive() {
        return this.acuiteAuditive;
    }

    public void setAcuiteAuditive(String acuiteAuditive) {
        this.acuiteAuditive = acuiteAuditive;
    }

    public String getAutresBilans() {
        return this.autresBilans;
    }

    public void setAutresBilans(String autresBilans) {
        this.autresBilans = autresBilans;
    }

    public String getDeveloppementAlimentation() {
        return this.developpementAlimentation;
    }

    public void setDeveloppementAlimentation(String developpementAlimentation) {
        this.developpementAlimentation = developpementAlimentation;
    }

    public String getDifficulteAliments() {
        return this.difficulteAliments;
    }

    public void setDifficulteAliments(String difficulteAliments) {
        this.difficulteAliments = difficulteAliments;
    }

    public boolean isRespireBoucheOuverte() {
        return this.respireBoucheOuverte;
    }

    public boolean getRespireBoucheOuverte() {
        return this.respireBoucheOuverte;
    }

    public void setRespireBoucheOuverte(boolean respireBoucheOuverte) {
        this.respireBoucheOuverte = respireBoucheOuverte;
    }

    public boolean isFatigueMatin() {
        return this.fatigueMatin;
    }

    public boolean getFatigueMatin() {
        return this.fatigueMatin;
    }

    public void setFatigueMatin(boolean fatigueMatin) {
        this.fatigueMatin = fatigueMatin;
    }

    public boolean isSucePouceTetine() {
        return this.sucePouceTetine;
    }

    public boolean getSucePouceTetine() {
        return this.sucePouceTetine;
    }

    public void setSucePouceTetine(boolean sucePouceTetine) {
        this.sucePouceTetine = sucePouceTetine;
    }

    public boolean isRonflement() {
        return this.ronflement;
    }

    public boolean getRonflement() {
        return this.ronflement;
    }

    public void setRonflement(boolean ronflement) {
        this.ronflement = ronflement;
    }

    public String getActivitesExtrascolaires() {
        return this.activitesExtrascolaires;
    }

    public void setActivitesExtrascolaires(String activitesExtrascolaires) {
        this.activitesExtrascolaires = activitesExtrascolaires;
    }

    public String getTempsEcran() {
        return this.tempsEcran;
    }

    public void setTempsEcran(String tempsEcran) {
        this.tempsEcran = tempsEcran;
    }

    // Autres constructeurs ou méthodes si nécessaire
}

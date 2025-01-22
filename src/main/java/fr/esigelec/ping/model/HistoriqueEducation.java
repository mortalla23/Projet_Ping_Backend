package fr.esigelec.ping.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "historique_education") // Associe ce modèle à la collection "historique_education" de MongoDB
public class HistoriqueEducation {


    public HistoriqueEducation(int id, String nom, String prenom, String dateNaissance, String niveauScolaire, String dominant, String situationFamiliale, String difficulteLangage, boolean bilanOrthophonique, String dateObservationDifficultes, boolean conscienceDifficultes, String souffranceDifficultes, boolean apprentissageLectureEcritureFacile, boolean apprecieLecture, boolean apprecieCalcul, String soutienScolaire, String matieresProblematique) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.niveauScolaire = niveauScolaire;
        this.dominant = dominant;
        this.situationFamiliale = situationFamiliale;
        this.difficulteLangage = difficulteLangage;
        this.bilanOrthophonique = bilanOrthophonique;
        this.dateObservationDifficultes = dateObservationDifficultes;
        this.conscienceDifficultes = conscienceDifficultes;
        this.souffranceDifficultes = souffranceDifficultes;
        this.apprentissageLectureEcritureFacile = apprentissageLectureEcritureFacile;
        this.apprecieLecture = apprecieLecture;
        this.apprecieCalcul = apprecieCalcul;
        this.soutienScolaire = soutienScolaire;
        this.matieresProblematique = matieresProblematique;
    }

    @Field("id")
    private int id;

    @Field("nom")
    private String nom;

    @Field("prenom")
    private String prenom;

    @Field("date_naissance")
    private String dateNaissance; // Date au format String (ou tu peux utiliser LocalDate si tu préfères)

    @Field("niveau_scolaire")
    private String niveauScolaire;

    @Field("dominant")
    private String dominant;

    @Field("situation_familiale")
    private String situationFamiliale;

    @Field("difficulte_langage")
    private String difficulteLangage;

    @Field("bilan_orthophonique")
    private boolean bilanOrthophonique;

    @Field("date_observation_difficultes")
    private String dateObservationDifficultes; // Date au format String (ou tu peux utiliser LocalDate)

    @Field("conscience_difficultes")
    private boolean conscienceDifficultes;

    @Field("souffrance_difficultes")
    private String souffranceDifficultes;

    @Field("apprentissage_lecture_ecriture_facile")
    private boolean apprentissageLectureEcritureFacile;

    @Field("apprecie_lecture")
    private boolean apprecieLecture;

    @Field("apprecie_calcul")
    private boolean apprecieCalcul;

    @Field("soutien_scolaire")
    private String soutienScolaire;

    @Field("matieres_problematique")
    private String matieresProblematique;

    // Constructeur par défaut
    public HistoriqueEducation() {}


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDateNaissance() {
        return this.dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNiveauScolaire() {
        return this.niveauScolaire;
    }

    public void setNiveauScolaire(String niveauScolaire) {
        this.niveauScolaire = niveauScolaire;
    }

    public String getDominant() {
        return this.dominant;
    }

    public void setDominant(String dominant) {
        this.dominant = dominant;
    }

    public String getSituationFamiliale() {
        return this.situationFamiliale;
    }

    public void setSituationFamiliale(String situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public String getDifficulteLangage() {
        return this.difficulteLangage;
    }

    public void setDifficulteLangage(String difficulteLangage) {
        this.difficulteLangage = difficulteLangage;
    }

    public boolean isBilanOrthophonique() {
        return this.bilanOrthophonique;
    }

    public boolean getBilanOrthophonique() {
        return this.bilanOrthophonique;
    }

    public void setBilanOrthophonique(boolean bilanOrthophonique) {
        this.bilanOrthophonique = bilanOrthophonique;
    }

    public String getDateObservationDifficultes() {
        return this.dateObservationDifficultes;
    }

    public void setDateObservationDifficultes(String dateObservationDifficultes) {
        this.dateObservationDifficultes = dateObservationDifficultes;
    }

    public boolean isConscienceDifficultes() {
        return this.conscienceDifficultes;
    }

    public boolean getConscienceDifficultes() {
        return this.conscienceDifficultes;
    }

    public void setConscienceDifficultes(boolean conscienceDifficultes) {
        this.conscienceDifficultes = conscienceDifficultes;
    }

    public String getSouffranceDifficultes() {
        return this.souffranceDifficultes;
    }

    public void setSouffranceDifficultes(String souffranceDifficultes) {
        this.souffranceDifficultes = souffranceDifficultes;
    }

    public boolean isApprentissageLectureEcritureFacile() {
        return this.apprentissageLectureEcritureFacile;
    }

    public boolean getApprentissageLectureEcritureFacile() {
        return this.apprentissageLectureEcritureFacile;
    }

    public void setApprentissageLectureEcritureFacile(boolean apprentissageLectureEcritureFacile) {
        this.apprentissageLectureEcritureFacile = apprentissageLectureEcritureFacile;
    }

    public boolean isApprecieLecture() {
        return this.apprecieLecture;
    }

    public boolean getApprecieLecture() {
        return this.apprecieLecture;
    }

    public void setApprecieLecture(boolean apprecieLecture) {
        this.apprecieLecture = apprecieLecture;
    }

    public boolean isApprecieCalcul() {
        return this.apprecieCalcul;
    }

    public boolean getApprecieCalcul() {
        return this.apprecieCalcul;
    }

    public void setApprecieCalcul(boolean apprecieCalcul) {
        this.apprecieCalcul = apprecieCalcul;
    }

    public String getSoutienScolaire() {
        return this.soutienScolaire;
    }

    public void setSoutienScolaire(String soutienScolaire) {
        this.soutienScolaire = soutienScolaire;
    }

    public String getMatieresProblematique() {
        return this.matieresProblematique;
    }

    public void setMatieresProblematique(String matieresProblematique) {
        this.matieresProblematique = matieresProblematique;
    }
    
}

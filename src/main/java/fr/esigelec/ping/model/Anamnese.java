package fr.esigelec.ping.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@Document(collection = "anamnese") // Associe ce modèle à la collection "anamnese" de MongoDB
public class Anamnese {
     

    @Field("id")
    private int id;

    @Field("consultation_demande_par")
    private String consultationDemandePar;

    @Field("motif_consultation")
    private String motifConsultation;

    @Field("partage_infos_equipe_pedagogique")
    private boolean partageInfosEquipePedagogique;

    @Field("partage_infos_autres_soignants")
    private boolean partageInfosAutresSoignants;

    @Field("suivi_orthophonie")
    private boolean suiviOrthophonie;

    @Field("suivi_orthophonie_details")
    private String suiviOrthophonieDetails;

    @Field("suivi_psychomotricite")
    private boolean suiviPsychomotricite;

    @Field("suivi_psychomotricite_details")
    private String suiviPsychomotriciteDetails;

    @Field("autres_suivis")
    private String autresSuivis;

    @Field("grossesse")
    private String grossesse;

    @Field("naissance_a_terme")
    private boolean naissanceATerme;

    @Field("naissance_a_terme_details")
    private String naissanceATermeDetails;

    @Field("poids_naissance")
    private float poidsNaissance;

    @Field("perimetre_cranien")
    private float perimetreCranien;

    @Field("intervention_vegetations")
    private boolean interventionVegetations;

    @Field("intervention_autres")
    private String interventionAutres;

    @Field("intervention_amygdales")
    private boolean interventionAmygdales;

    @Field("hospitalisations")
    private boolean hospitalisations;

    @Field("hospitalisations_details")
    private String hospitalisationsDetails;

    @Field("angines")
    private boolean angines;

    @Field("rhino-pharyngites")
    private boolean rhinoPharyngites;

    @Field("otites")
    private boolean otites;

    @Field("asthme")
    private boolean asthme;

    @Field("bronchites")
    private boolean bronchites;

    @Field("problemes_pulmonaires")
    private boolean problemesPulmonaires;

    @Field("autres_maladies")
    private String autresMaladies;

    @Field("allergies")
    private boolean allergies;

    @Field("allergies_details")
    private String allergiesDetails;

    @Field("bilans_acuite_visuelle")
    private boolean bilansAcuiteVisuelle;

    @Field("bilans_acuite_visuelle_date")
    private Date bilansAcuiteVisuelleDate;

    @Field("bilans_acuite_auditive")
    private boolean bilansAcuiteAuditive;

    @Field("bilans_acuite_auditive_date")
    private Date bilansAcuiteAuditiveDate;

    @Field("autres_bilans")
    private String autresBilans;

    @Field("posture_assise_date_age")
    private String postureAssiseDateAge;

    @Field("marche_quatre_pattes_date_age")
    private String marcheQuatrePattesDateAge;

    @Field("marche_date_age")
    private String marcheDateAge;

    @Field("propre_jour_date_age")
    private String propreJourDateAge;

    @Field("propre_nuit_date_age")
    private String propreNuitDateAge;

    @Field("premiers_mots_date_age")
    private String premiersMotsDateAge;

    @Field("premieres_phrases_date_age")
    private String premieresPhrasesDateAge;

    @Field("difficulte_signalees")
    private String difficulteSignalees;

    @Field("habille_seul")
    private boolean habilleSeul;

    @Field("lave_seul")
    private boolean laveSeul;

    @Field("suce_pouce_tetine")
    private boolean sucePouceTetine;

    @Field("jouet_peluche_pour_dormir")
    private boolean jouetPeluchePourDormir;

    @Field("caractere_volontaire")
    private boolean caractereVolontaire;

    @Field("caractere_se_decourage")
    private boolean caractereSeDecourage;

    @Field("caractere_anxieux")
    private boolean caractereAnxieux;

    @Field("caractere_nerveux")
    private boolean caractereNerveux;

    @Field("caractere_agite")
    private boolean caractereAgite;

    @Field("caractere_calme")
    private boolean caractereCalme;

    @Field("caractere_timide")
    private boolean caractereTimide;

    @Field("caractere_confiance")
    private boolean caractereConfiance;

    @Field("caractere_rapide")
    private boolean caractereRapide;

    @Field("caractere_lent")
    private boolean caractereLent;

    @Field("caractere_ronge_ongles")
    private boolean caractereRongeOngles;

    @Field("caractere_tics")
    private boolean caractereTics;

    @Field("caractere_sociable")
    private boolean caractereSociable;

    @Field("caractere_serviable")
    private boolean caractereServiable;

    @Field("caractere_calin")
    private boolean caractereCalin;

    @Field("caractere_emotif")
    private boolean caractereEmotif;

    @Field("caractere_sensible")
    private boolean caractereSensible;

    @Field("caractere_jaloux")
    private boolean caractereJaloux;

    @Field("caractere_raconte_facilement")
    private boolean caractereRaconteFacilement;

    @Field("caractere_autres")
    private String caractereAutres;

    @Field("situation_familiale")
    private String situationFamiliale;

    @Field("freres")
    private String freres;

    @Field("soeurs")
    private String soeurs;

    @Field("chambre_partagee")
    private boolean chambrePartagee;

    @Field("repas_communs")
    private String repasCommuns;

    @Field("activites_extrascolaires")
    private boolean activitesExtrascolaires;

    @Field("activites_extrascolaires_details")
    private String activitesExtrascolairesDetails;

    @Field("activites_preferees")
    private String activitesPreferees;

    // Constructeur par défaut
    public Anamnese() {}

    // Autres constructeurs ou méthodes si nécessaire

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConsultationDemandePar() {
        return this.consultationDemandePar;
    }

    public void setConsultationDemandePar(String consultationDemandePar) {
        this.consultationDemandePar = consultationDemandePar;
    }

    public String getMotifConsultation() {
        return this.motifConsultation;
    }

    public void setMotifConsultation(String motifConsultation) {
        this.motifConsultation = motifConsultation;
    }

    public boolean isPartageInfosEquipePedagogique() {
        return this.partageInfosEquipePedagogique;
    }

    public boolean getPartageInfosEquipePedagogique() {
        return this.partageInfosEquipePedagogique;
    }

    public void setPartageInfosEquipePedagogique(boolean partageInfosEquipePedagogique) {
        this.partageInfosEquipePedagogique = partageInfosEquipePedagogique;
    }

    public boolean isPartageInfosAutresSoignants() {
        return this.partageInfosAutresSoignants;
    }

    public boolean getPartageInfosAutresSoignants() {
        return this.partageInfosAutresSoignants;
    }

    public void setPartageInfosAutresSoignants(boolean partageInfosAutresSoignants) {
        this.partageInfosAutresSoignants = partageInfosAutresSoignants;
    }

    public boolean isSuiviOrthophonie() {
        return this.suiviOrthophonie;
    }

    public boolean getSuiviOrthophonie() {
        return this.suiviOrthophonie;
    }

    public void setSuiviOrthophonie(boolean suiviOrthophonie) {
        this.suiviOrthophonie = suiviOrthophonie;
    }

    public String getSuiviOrthophonieDetails() {
        return this.suiviOrthophonieDetails;
    }

    public void setSuiviOrthophonieDetails(String suiviOrthophonieDetails) {
        this.suiviOrthophonieDetails = suiviOrthophonieDetails;
    }

    public boolean isSuiviPsychomotricite() {
        return this.suiviPsychomotricite;
    }

    public boolean getSuiviPsychomotricite() {
        return this.suiviPsychomotricite;
    }

    public void setSuiviPsychomotricite(boolean suiviPsychomotricite) {
        this.suiviPsychomotricite = suiviPsychomotricite;
    }

    public String getSuiviPsychomotriciteDetails() {
        return this.suiviPsychomotriciteDetails;
    }

    public void setSuiviPsychomotriciteDetails(String suiviPsychomotriciteDetails) {
        this.suiviPsychomotriciteDetails = suiviPsychomotriciteDetails;
    }

    public String getAutresSuivis() {
        return this.autresSuivis;
    }

    public void setAutresSuivis(String autresSuivis) {
        this.autresSuivis = autresSuivis;
    }

    public String getGrossesse() {
        return this.grossesse;
    }

    public void setGrossesse(String grossesse) {
        this.grossesse = grossesse;
    }

    public boolean isNaissanceATerme() {
        return this.naissanceATerme;
    }

    public boolean getNaissanceATerme() {
        return this.naissanceATerme;
    }

    public void setNaissanceATerme(boolean naissanceATerme) {
        this.naissanceATerme = naissanceATerme;
    }

    public String getNaissanceATermeDetails() {
        return this.naissanceATermeDetails;
    }

    public void setNaissanceATermeDetails(String naissanceATermeDetails) {
        this.naissanceATermeDetails = naissanceATermeDetails;
    }

    public float getPoidsNaissance() {
        return this.poidsNaissance;
    }

    public void setPoidsNaissance(float poidsNaissance) {
        this.poidsNaissance = poidsNaissance;
    }

    public float getPerimetreCranien() {
        return this.perimetreCranien;
    }

    public void setPerimetreCranien(float perimetreCranien) {
        this.perimetreCranien = perimetreCranien;
    }

    public boolean isInterventionVegetations() {
        return this.interventionVegetations;
    }

    public boolean getInterventionVegetations() {
        return this.interventionVegetations;
    }

    public void setInterventionVegetations(boolean interventionVegetations) {
        this.interventionVegetations = interventionVegetations;
    }

    public String getInterventionAutres() {
        return this.interventionAutres;
    }

    public void setInterventionAutres(String interventionAutres) {
        this.interventionAutres = interventionAutres;
    }

    public boolean isInterventionAmygdales() {
        return this.interventionAmygdales;
    }

    public boolean getInterventionAmygdales() {
        return this.interventionAmygdales;
    }

    public void setInterventionAmygdales(boolean interventionAmygdales) {
        this.interventionAmygdales = interventionAmygdales;
    }

    public boolean isHospitalisations() {
        return this.hospitalisations;
    }

    public boolean getHospitalisations() {
        return this.hospitalisations;
    }

    public void setHospitalisations(boolean hospitalisations) {
        this.hospitalisations = hospitalisations;
    }

    public String getHospitalisationsDetails() {
        return this.hospitalisationsDetails;
    }

    public void setHospitalisationsDetails(String hospitalisationsDetails) {
        this.hospitalisationsDetails = hospitalisationsDetails;
    }

    public boolean isAngines() {
        return this.angines;
    }

    public boolean getAngines() {
        return this.angines;
    }

    public void setAngines(boolean angines) {
        this.angines = angines;
    }

    public boolean isRhinoPharyngites() {
        return this.rhinoPharyngites;
    }

    public boolean getRhinoPharyngites() {
        return this.rhinoPharyngites;
    }

    public void setRhinoPharyngites(boolean rhinoPharyngites) {
        this.rhinoPharyngites = rhinoPharyngites;
    }

    public boolean isOtites() {
        return this.otites;
    }

    public boolean getOtites() {
        return this.otites;
    }

    public void setOtites(boolean otites) {
        this.otites = otites;
    }

    public boolean isAsthme() {
        return this.asthme;
    }

    public boolean getAsthme() {
        return this.asthme;
    }

    public void setAsthme(boolean asthme) {
        this.asthme = asthme;
    }

    public boolean isBronchites() {
        return this.bronchites;
    }

    public boolean getBronchites() {
        return this.bronchites;
    }

    public void setBronchites(boolean bronchites) {
        this.bronchites = bronchites;
    }

    public boolean isProblemesPulmonaires() {
        return this.problemesPulmonaires;
    }

    public boolean getProblemesPulmonaires() {
        return this.problemesPulmonaires;
    }

    public void setProblemesPulmonaires(boolean problemesPulmonaires) {
        this.problemesPulmonaires = problemesPulmonaires;
    }

    public String getAutresMaladies() {
        return this.autresMaladies;
    }

    public void setAutresMaladies(String autresMaladies) {
        this.autresMaladies = autresMaladies;
    }

    public boolean isAllergies() {
        return this.allergies;
    }

    public boolean getAllergies() {
        return this.allergies;
    }

    public void setAllergies(boolean allergies) {
        this.allergies = allergies;
    }

    public String getAllergiesDetails() {
        return this.allergiesDetails;
    }

    public void setAllergiesDetails(String allergiesDetails) {
        this.allergiesDetails = allergiesDetails;
    }

    public boolean isBilansAcuiteVisuelle() {
        return this.bilansAcuiteVisuelle;
    }

    public boolean getBilansAcuiteVisuelle() {
        return this.bilansAcuiteVisuelle;
    }

    public void setBilansAcuiteVisuelle(boolean bilansAcuiteVisuelle) {
        this.bilansAcuiteVisuelle = bilansAcuiteVisuelle;
    }

    public Date getBilansAcuiteVisuelleDate() {
        return this.bilansAcuiteVisuelleDate;
    }

    public void setBilansAcuiteVisuelleDate(Date bilansAcuiteVisuelleDate) {
        this.bilansAcuiteVisuelleDate = bilansAcuiteVisuelleDate;
    }

    public boolean isBilansAcuiteAuditive() {
        return this.bilansAcuiteAuditive;
    }

    public boolean getBilansAcuiteAuditive() {
        return this.bilansAcuiteAuditive;
    }

    public void setBilansAcuiteAuditive(boolean bilansAcuiteAuditive) {
        this.bilansAcuiteAuditive = bilansAcuiteAuditive;
    }

    public Date getBilansAcuiteAuditiveDate() {
        return this.bilansAcuiteAuditiveDate;
    }

    public void setBilansAcuiteAuditiveDate(Date bilansAcuiteAuditiveDate) {
        this.bilansAcuiteAuditiveDate = bilansAcuiteAuditiveDate;
    }

    public String getAutresBilans() {
        return this.autresBilans;
    }

    public void setAutresBilans(String autresBilans) {
        this.autresBilans = autresBilans;
    }

    public String getPostureAssiseDateAge() {
        return this.postureAssiseDateAge;
    }

    public void setPostureAssiseDateAge(String postureAssiseDateAge) {
        this.postureAssiseDateAge = postureAssiseDateAge;
    }

    public String getMarcheQuatrePattesDateAge() {
        return this.marcheQuatrePattesDateAge;
    }

    public void setMarcheQuatrePattesDateAge(String marcheQuatrePattesDateAge) {
        this.marcheQuatrePattesDateAge = marcheQuatrePattesDateAge;
    }

    public String getMarcheDateAge() {
        return this.marcheDateAge;
    }

    public void setMarcheDateAge(String marcheDateAge) {
        this.marcheDateAge = marcheDateAge;
    }

    public String getPropreJourDateAge() {
        return this.propreJourDateAge;
    }

    public void setPropreJourDateAge(String propreJourDateAge) {
        this.propreJourDateAge = propreJourDateAge;
    }

    public String getPropreNuitDateAge() {
        return this.propreNuitDateAge;
    }

    public void setPropreNuitDateAge(String propreNuitDateAge) {
        this.propreNuitDateAge = propreNuitDateAge;
    }

    public String getPremiersMotsDateAge() {
        return this.premiersMotsDateAge;
    }

    public void setPremiersMotsDateAge(String premiersMotsDateAge) {
        this.premiersMotsDateAge = premiersMotsDateAge;
    }

    public String getPremieresPhrasesDateAge() {
        return this.premieresPhrasesDateAge;
    }

    public void setPremieresPhrasesDateAge(String premieresPhrasesDateAge) {
        this.premieresPhrasesDateAge = premieresPhrasesDateAge;
    }

    public String getDifficulteSignalees() {
        return this.difficulteSignalees;
    }

    public void setDifficulteSignalees(String difficulteSignalees) {
        this.difficulteSignalees = difficulteSignalees;
    }

    public boolean isHabilleSeul() {
        return this.habilleSeul;
    }

    public boolean getHabilleSeul() {
        return this.habilleSeul;
    }

    public void setHabilleSeul(boolean habilleSeul) {
        this.habilleSeul = habilleSeul;
    }

    public boolean isLaveSeul() {
        return this.laveSeul;
    }

    public boolean getLaveSeul() {
        return this.laveSeul;
    }

    public void setLaveSeul(boolean laveSeul) {
        this.laveSeul = laveSeul;
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

    public boolean isJouetPeluchePourDormir() {
        return this.jouetPeluchePourDormir;
    }

    public boolean getJouetPeluchePourDormir() {
        return this.jouetPeluchePourDormir;
    }

    public void setJouetPeluchePourDormir(boolean jouetPeluchePourDormir) {
        this.jouetPeluchePourDormir = jouetPeluchePourDormir;
    }

    public boolean isCaractereVolontaire() {
        return this.caractereVolontaire;
    }

    public boolean getCaractereVolontaire() {
        return this.caractereVolontaire;
    }

    public void setCaractereVolontaire(boolean caractereVolontaire) {
        this.caractereVolontaire = caractereVolontaire;
    }

    public boolean isCaractereSeDecourage() {
        return this.caractereSeDecourage;
    }

    public boolean getCaractereSeDecourage() {
        return this.caractereSeDecourage;
    }

    public void setCaractereSeDecourage(boolean caractereSeDecourage) {
        this.caractereSeDecourage = caractereSeDecourage;
    }

    public boolean isCaractereAnxieux() {
        return this.caractereAnxieux;
    }

    public boolean getCaractereAnxieux() {
        return this.caractereAnxieux;
    }

    public void setCaractereAnxieux(boolean caractereAnxieux) {
        this.caractereAnxieux = caractereAnxieux;
    }

    public boolean isCaractereNerveux() {
        return this.caractereNerveux;
    }

    public boolean getCaractereNerveux() {
        return this.caractereNerveux;
    }

    public void setCaractereNerveux(boolean caractereNerveux) {
        this.caractereNerveux = caractereNerveux;
    }

    public boolean isCaractereAgite() {
        return this.caractereAgite;
    }

    public boolean getCaractereAgite() {
        return this.caractereAgite;
    }

    public void setCaractereAgite(boolean caractereAgite) {
        this.caractereAgite = caractereAgite;
    }

    public boolean isCaractereCalme() {
        return this.caractereCalme;
    }

    public boolean getCaractereCalme() {
        return this.caractereCalme;
    }

    public void setCaractereCalme(boolean caractereCalme) {
        this.caractereCalme = caractereCalme;
    }

    public boolean isCaractereTimide() {
        return this.caractereTimide;
    }

    public boolean getCaractereTimide() {
        return this.caractereTimide;
    }

    public void setCaractereTimide(boolean caractereTimide) {
        this.caractereTimide = caractereTimide;
    }

    public boolean isCaractereConfiance() {
        return this.caractereConfiance;
    }

    public boolean getCaractereConfiance() {
        return this.caractereConfiance;
    }

    public void setCaractereConfiance(boolean caractereConfiance) {
        this.caractereConfiance = caractereConfiance;
    }

    public boolean isCaractereRapide() {
        return this.caractereRapide;
    }

    public boolean getCaractereRapide() {
        return this.caractereRapide;
    }

    public void setCaractereRapide(boolean caractereRapide) {
        this.caractereRapide = caractereRapide;
    }

    public boolean isCaractereLent() {
        return this.caractereLent;
    }

    public boolean getCaractereLent() {
        return this.caractereLent;
    }

    public void setCaractereLent(boolean caractereLent) {
        this.caractereLent = caractereLent;
    }

    public boolean isCaractereRongeOngles() {
        return this.caractereRongeOngles;
    }

    public boolean getCaractereRongeOngles() {
        return this.caractereRongeOngles;
    }

    public void setCaractereRongeOngles(boolean caractereRongeOngles) {
        this.caractereRongeOngles = caractereRongeOngles;
    }

    public boolean isCaractereTics() {
        return this.caractereTics;
    }

    public boolean getCaractereTics() {
        return this.caractereTics;
    }

    public void setCaractereTics(boolean caractereTics) {
        this.caractereTics = caractereTics;
    }

    public boolean isCaractereSociable() {
        return this.caractereSociable;
    }

    public boolean getCaractereSociable() {
        return this.caractereSociable;
    }

    public void setCaractereSociable(boolean caractereSociable) {
        this.caractereSociable = caractereSociable;
    }

    public boolean isCaractereServiable() {
        return this.caractereServiable;
    }

    public boolean getCaractereServiable() {
        return this.caractereServiable;
    }

    public void setCaractereServiable(boolean caractereServiable) {
        this.caractereServiable = caractereServiable;
    }

    public boolean isCaractereCalin() {
        return this.caractereCalin;
    }

    public boolean getCaractereCalin() {
        return this.caractereCalin;
    }

    public void setCaractereCalin(boolean caractereCalin) {
        this.caractereCalin = caractereCalin;
    }

    public boolean isCaractereEmotif() {
        return this.caractereEmotif;
    }

    public boolean getCaractereEmotif() {
        return this.caractereEmotif;
    }

    public void setCaractereEmotif(boolean caractereEmotif) {
        this.caractereEmotif = caractereEmotif;
    }

    public boolean isCaractereSensible() {
        return this.caractereSensible;
    }

    public boolean getCaractereSensible() {
        return this.caractereSensible;
    }

    public void setCaractereSensible(boolean caractereSensible) {
        this.caractereSensible = caractereSensible;
    }

    public boolean isCaractereJaloux() {
        return this.caractereJaloux;
    }

    public boolean getCaractereJaloux() {
        return this.caractereJaloux;
    }

    public void setCaractereJaloux(boolean caractereJaloux) {
        this.caractereJaloux = caractereJaloux;
    }

    public boolean isCaractereRaconteFacilement() {
        return this.caractereRaconteFacilement;
    }

    public boolean getCaractereRaconteFacilement() {
        return this.caractereRaconteFacilement;
    }

    public void setCaractereRaconteFacilement(boolean caractereRaconteFacilement) {
        this.caractereRaconteFacilement = caractereRaconteFacilement;
    }

    public String getCaractereAutres() {
        return this.caractereAutres;
    }

    public void setCaractereAutres(String caractereAutres) {
        this.caractereAutres = caractereAutres;
    }

    public String getSituationFamiliale() {
        return this.situationFamiliale;
    }

    public void setSituationFamiliale(String situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }

    public String getFreres() {
        return this.freres;
    }

    public void setFreres(String freres) {
        this.freres = freres;
    }

    public String getSoeurs() {
        return this.soeurs;
    }

    public void setSoeurs(String soeurs) {
        this.soeurs = soeurs;
    }

    public boolean isChambrePartagee() {
        return this.chambrePartagee;
    }

    public boolean getChambrePartagee() {
        return this.chambrePartagee;
    }

    public void setChambrePartagee(boolean chambrePartagee) {
        this.chambrePartagee = chambrePartagee;
    }

    public String getRepasCommuns() {
        return this.repasCommuns;
    }

    public void setRepasCommuns(String repasCommuns) {
        this.repasCommuns = repasCommuns;
    }

    public boolean isActivitesExtrascolaires() {
        return this.activitesExtrascolaires;
    }

    public boolean getActivitesExtrascolaires() {
        return this.activitesExtrascolaires;
    }

    public void setActivitesExtrascolaires(boolean activitesExtrascolaires) {
        this.activitesExtrascolaires = activitesExtrascolaires;
    }

    public String getActivitesExtrascolairesDetails() {
        return this.activitesExtrascolairesDetails;
    }

    public void setActivitesExtrascolairesDetails(String activitesExtrascolairesDetails) {
        this.activitesExtrascolairesDetails = activitesExtrascolairesDetails;
    }

    public String getActivitesPreferees() {
        return this.activitesPreferees;
    }

    public void setActivitesPreferees(String activitesPreferees) {
        this.activitesPreferees = activitesPreferees;
    }

    
}

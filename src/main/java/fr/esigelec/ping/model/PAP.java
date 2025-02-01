package fr.esigelec.ping.model;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pap")  // Associe ce modèle à la collection "conversations" de MongoDB
public class PAP {

    
    @Field("id")  // Mappé au champ "id" de MongoDB
    private int id;

    @Field("user_id")  // Mappé au champ "is_public" de MongoDB
    private int userId;


    @Field("responsables")  // Mappé au champ "is_public" de MongoDB
    private String responsables;

    @Field("strengths")  // Mappé au champ "last_message_id" de MongoDB
    private String strengths;

    @Field("challenges")  // Mappé au champ "last_message_id" de MongoDB
    private String challenges;

    @Field("history")  // Mappé au champ "last_message_id" de MongoDB
    private String history;

    @Field("short_term_goals")  // Mappé au champ "last_message_id" de MongoDB
    private String shortTermGoals;

    @Field("long_term_goals")  // Mappé au champ "last_message_id" de MongoDB
    private String longTermGoals;

    @Field("progress_evaluation")  // Mappé au champ "last_message_id" de MongoDB
    private String progressEvaluation;

    @Field("ressources_needed")  // Mappé au champ "last_message_id" de MongoDB
    private String ressourcesNeeded;

    @Field("parent_feed_back")  // Mappé au champ "last_message_id" de MongoDB
    private String parentFeedBack;

    @Field("observations")  // Mappé au champ "created_at" de MongoDB
    private String observations;


    @Field("follow_up")  // Mappé au champ "created_at" de MongoDB
    private String followUp;

    @Field("updated_at")  // Mappé au champ "created_at" de MongoDB
    private Date updatedAt;
    

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getResponsables() {
        return this.responsables;
    }

    public void setResponsables(String responsables) {
        this.responsables = responsables;
    }

    public String getStrengths() {
        return this.strengths;
    }

    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }

    public String getChallenges() {
        return this.challenges;
    }

    public void setChallenges(String challenges) {
        this.challenges = challenges;
    }

    public String getHistory() {
        return this.history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getShortTermGoals() {
        return this.shortTermGoals;
    }

    public void setShortTermGoals(String shortTermGoals) {
        this.shortTermGoals = shortTermGoals;
    }

    public String getLongTermGoals() {
        return this.longTermGoals;
    }

    public void setLongTermGoals(String longTermGoals) {
        this.longTermGoals = longTermGoals;
    }

    public String getProgressEvaluation() {
        return this.progressEvaluation;
    }

    public void setProgressEvaluation(String progressEvaluation) {
        this.progressEvaluation = progressEvaluation;
    }

    public String getRessourcesNeeded() {
        return this.ressourcesNeeded;
    }

    public void setRessourcesNeeded(String ressourcesNeeded) {
        this.ressourcesNeeded = ressourcesNeeded;
    }

    public String getParentFeedBack() {
        return this.parentFeedBack;
    }

    public void setParentFeedBack(String parentFeedBack) {
        this.parentFeedBack = parentFeedBack;
    }

    public String getObservations() {
        return this.observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getFollowUp() {
        return this.followUp;
    }

    public void setFollowUp(String followUp) {
        this.followUp = followUp;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    

    
}

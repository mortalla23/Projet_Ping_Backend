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
    
    

    
}

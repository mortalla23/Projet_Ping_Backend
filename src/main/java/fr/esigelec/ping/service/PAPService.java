package fr.esigelec.ping.service;

import fr.esigelec.ping.model.PAP;
import fr.esigelec.ping.repository.PAPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


import org.springframework.data.mongodb.core.query.Query;
@Service
public class PAPService {

    @Autowired
    private PAPRepository papRepository;

     @Autowired
    private MongoTemplate mongoTemplate;

    public PAP createPAP(PAP pap) {
        pap.setId(generateUniquePAPId());
        pap.setUpdatedAt(new Date());
        return papRepository.save(pap);
    }

    public Optional<PAP> getPAPById(int id) {
        return papRepository.findById(id);
    }

    public List<PAP> getPAPByUserId(int userId) {
        return papRepository.findByUserId(userId);
    }

   // Méthode pour mettre à jour un PAP
    public PAP updatePAP(int id, PAP updatedPAP) {
        // Construire la requête pour trouver le document avec l'id correspondant
        Query query = new Query(Criteria.where("id").is(id));

        // Définir les champs à mettre à jour
        Update update = new Update()
            .set("responsables", updatedPAP.getResponsables())
            .set("strengths", updatedPAP.getStrengths())
            .set("challenges", updatedPAP.getChallenges())
            .set("history", updatedPAP.getHistory())
            .set("short_term_goals", updatedPAP.getShortTermGoals())
            .set("long_term_goals", updatedPAP.getLongTermGoals())
            .set("progress_evaluation", updatedPAP.getProgressEvaluation())
            .set("ressources_needed", updatedPAP.getRessourcesNeeded())
            .set("parent_feed_back", updatedPAP.getParentFeedBack())
            .set("observations", updatedPAP.getObservations())
            .set("follow_up", updatedPAP.getFollowUp())
            .set("updated_at", new Date());

        // Mettre à jour le premier document correspondant
        mongoTemplate.updateFirst(query, update, PAP.class);

        // Retourner le document mis à jour
        return mongoTemplate.findOne(query, PAP.class);
    }

    public void deletePAP(int id) {
        papRepository.deleteById(id);
    }

    // 🔄 Génération d'un ID unique pour le message
    private int generateUniquePAPId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (papRepository.existsById(id));
        return id;
    }
     
}

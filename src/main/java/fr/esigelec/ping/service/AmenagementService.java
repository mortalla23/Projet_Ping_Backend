package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Amenagement;
import fr.esigelec.ping.model.Message;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.AmenagementValidation;
import fr.esigelec.ping.repository.AmenagementRepository;
import fr.esigelec.ping.repository.UserRepository;
import fr.esigelec.ping.model.enums.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;





import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class AmenagementService {

    @Autowired
    private AmenagementRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    // Créer un nouvel aménagement
    public Amenagement createAmenagement(Amenagement amenagement) {
        amenagement.setCreatedAt(new Date());
        amenagement.setUpdatedAt(new Date());
        amenagement.setId(generateAmenagementId());
        amenagement.setStatus(AmenagementValidation.ONGOING);
        
        //On récupère le prescripteur et si c'est un orthophoniste, on valide la prescription
        User user  = userService.getUserById(amenagement.getIdPrescripteur());
        if(user.getRole()==Role.ORTHOPHONIST)
        {
            amenagement.setStatus(AmenagementValidation.VALIDATED);
        }
        else
        {
            amenagement.setStatus(AmenagementValidation.ONGOING);
        }
            
        return repository.save(amenagement);
    }

   

    // 🔄 Marquer un message comme lu en ciblant le champ "id"
    public void validateAmenagement(int amenagementId) {
        // 📌 Crée une requête qui cible le champ "id" (ton identifiant métier)
        Query query = new Query(Criteria.where("id").is(amenagementId));

        // 🔄 Mise à jour du champ "isRead"
        Update update = new Update().set("status", AmenagementValidation.VALIDATED);

        // ⚡ Exécuter la mise à jour sans utiliser _id
        mongoTemplate.updateFirst(query, update, Amenagement.class);
    }

    // Supprimer un aménagement
    public void deleteAmenagement(int id) {
        repository.deleteById(id);
    }

    // Récupérer un aménagement par ID
    public Optional<Amenagement> getAmenagementById(int id) {
        return repository.findById(id);
    }

    // Récupérer tous les aménagements pour un utilisateur
    public List<Amenagement> getAmenagementsByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    // Récupérer tous les aménagements pour un prescripteur
    public List<Amenagement> getAmenagementsByPrescripteur(int idPrescripteur) {
        return repository.findByIdPrescripteur(idPrescripteur);
    }

     // 🔄 Génération d'un ID unique pour le message
     private int generateAmenagementId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (repository.existsById(id));
        return id;
    }
    
}

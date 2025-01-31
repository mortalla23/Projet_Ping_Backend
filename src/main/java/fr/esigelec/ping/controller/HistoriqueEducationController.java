package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.HistoriqueEducation;
import fr.esigelec.ping.repository.HistoriqueEducationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/historique-education")
public class HistoriqueEducationController {

    @Autowired
    private HistoriqueEducationRepository educationRepository;

    // Endpoint pour créer ou mettre à jour un enregistrement d'éducation
    @PostMapping
    public ResponseEntity<HistoriqueEducation> createOrUpdateEducation(@RequestBody HistoriqueEducation education) {
        try {
            if(educationRepository.existsById(education.getId())){
            educationRepository.deleteById(education.getId());
            }
            else{

                education.setId(generateUniqueUserId());
            }
            HistoriqueEducation savedEducation = educationRepository.save(education);
            return new ResponseEntity<>(savedEducation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint pour récupérer un enregistrement d'éducation par ID
    @GetMapping("/{id}")
    public ResponseEntity<HistoriqueEducation> getEducationById(@PathVariable int id) {
        Optional<HistoriqueEducation> education = Optional.of(educationRepository.findById(id));
        if (education.isPresent()) {
            return new ResponseEntity<>(education.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour supprimer un enregistrement d'éducation par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducationById(@PathVariable int id) {
        if (educationRepository.existsById(id)) {
            educationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     // 🔄 Génération d'un ID unique pour le message
private int generateUniqueUserId() {
    int id;
    do {
        id = (int) (Math.random() * 100000);
    } while (educationRepository.existsById(id));
    return id;
}
}

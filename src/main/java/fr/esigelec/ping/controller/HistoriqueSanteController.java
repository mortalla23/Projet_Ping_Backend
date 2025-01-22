package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.HistoriqueSante;
import fr.esigelec.ping.repository.HistoriqueSanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/historique-sante")
public class HistoriqueSanteController {

    @Autowired
    private HistoriqueSanteRepository historiqueSanteRepository;

    // Endpoint pour créer ou mettre à jour un historique de santé
    @PostMapping
    public ResponseEntity<HistoriqueSante> createOrUpdateHistoriqueSante(@RequestBody HistoriqueSante historiqueSante) {
        try {
            if(historiqueSanteRepository.existsById(historiqueSante.getId())){
                historiqueSanteRepository.deleteById(historiqueSante.getId());
                }
                else{
    
                    historiqueSante.setId(generateUniqueUserId());
                }HistoriqueSante savedHistoriqueSante = historiqueSanteRepository.save(historiqueSante);
            return new ResponseEntity<>(savedHistoriqueSante, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint pour récupérer un historique de santé par ID
    @GetMapping("/{id}")
    public ResponseEntity<HistoriqueSante> getHistoriqueSanteById(@PathVariable int id) {
        Optional<HistoriqueSante> historiqueSante = Optional.of(historiqueSanteRepository.findById(id));
        if (historiqueSante.isPresent()) {
            return new ResponseEntity<>(historiqueSante.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint pour supprimer un historique de santé par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistoriqueSanteById(@PathVariable int id) {
        if (historiqueSanteRepository.existsById(id)) {
            historiqueSanteRepository.deleteById(id);
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
    } while (historiqueSanteRepository.existsById(id));
    return id;
}
}

package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.PAP;
import fr.esigelec.ping.service.PAPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pap")
public class PAPController {

    @Autowired
    private PAPService papService;

    // 1. Créer un nouveau PAP
    @PostMapping("/create")
    public ResponseEntity<PAP> createPAP(@RequestBody PAP pap) {
        PAP createdPAP = papService.createPAP(pap);
        return ResponseEntity.ok(createdPAP);
    }

    // 2. Récupérer un PAP par son ID
    @GetMapping("/{id}")
    public ResponseEntity<PAP> getPAPById(@PathVariable int id) {
        Optional<PAP> pap = papService.getPAPById(id);
        return pap.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 3. Récupérer les PAP par user_id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PAP>> getPAPByUserId(@PathVariable int userId) {
        List<PAP> papList = papService.getPAPByUserId(userId);
        return ResponseEntity.ok(papList);
    }

    // 4. Mettre à jour un PAP existant
    @PutMapping("/update/{id}")
    public ResponseEntity<PAP> updatePAP(@PathVariable("id") int id, @RequestBody PAP pap) {
        Optional<PAP> existingPAP = papService.getPAPById(id);
        if (existingPAP.isPresent()) {
            PAP updatedPAP = papService.updatePAP(id,pap);
            return ResponseEntity.ok(updatedPAP);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Supprimer un PAP
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePAP(@PathVariable int id) {
        papService.deletePAP(id);
        return ResponseEntity.noContent().build();
    }
}

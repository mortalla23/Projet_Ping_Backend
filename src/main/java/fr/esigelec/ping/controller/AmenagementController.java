package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.Amenagement;
import fr.esigelec.ping.service.AmenagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/amenagements")
public class AmenagementController {

    @Autowired
    private AmenagementService service;

    // Endpoint pour créer un nouvel aménagement
    @PostMapping("/{create}")
    public ResponseEntity<Amenagement> createAmenagement(@RequestBody Amenagement amenagement) {
        Amenagement created = service.createAmenagement(amenagement);
        return ResponseEntity.ok(created);
    }

    // Endpoint pour mettre à jour un aménagement
    /*@PutMapping("/update/{id}")
    public ResponseEntity<Amenagement> updateAmenagement(@PathVariable("id") int id, @RequestBody Amenagement amenagement) {
        Amenagement updated = service.updateAmenagement(id, amenagement);
        return ResponseEntity.ok(updated);
    }*/

         // ✅ Endpoint pour marquer un message comme lu via POST (plus flexible)
@PostMapping("/validate")
public ResponseEntity<?> markMessageAsRead(@RequestParam int amenagementId) {
    try {
        if (amenagementId <= 0) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "L'ID du message est invalide."));
        }

        service.validateAmenagement(amenagementId);
        return ResponseEntity.ok(Collections.singletonMap("message", "L'aménagement a été validé'"));

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(Collections.singletonMap("message", "Erreur lors de la mise à jour de l'aménagement."));
    }
}


    // Endpoint pour supprimer un aménagement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenagement(@PathVariable int id) {
        service.deleteAmenagement(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint pour récupérer un aménagement par ID
    @GetMapping("/{id}")
    public ResponseEntity<Amenagement> getAmenagementById(@PathVariable int id) {
        Optional<Amenagement> amenagement = service.getAmenagementById(id);
        return amenagement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint pour récupérer tous les aménagements d'un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Amenagement>> getAmenagementsByUserId(@PathVariable int userId) {
        List<Amenagement> amenagements = service.getAmenagementsByUserId(userId);
        return ResponseEntity.ok(amenagements);
    }

    // Endpoint pour récupérer tous les aménagements d'un prescripteur
    @GetMapping("/prescripteur/{idPrescripteur}")
    public ResponseEntity<List<Amenagement>> getAmenagementsByPrescripteur(@PathVariable int idPrescripteur) {
        List<Amenagement> amenagements = service.getAmenagementsByPrescripteur(idPrescripteur);
        return ResponseEntity.ok(amenagements);
    }
}

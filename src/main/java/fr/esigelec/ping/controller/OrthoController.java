package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.service.OrthoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orthophoniste")
@CrossOrigin(origins = "http://localhost:3000") // Permettre l'accès depuis React.js
public class OrthoController {

    @Autowired
    private OrthoService orthoService;

    // Récupérer tous les patients associés à l'orthophoniste
    @GetMapping("/patients")
    public ResponseEntity<List<User>> getAllStudents(@RequestParam String orthoId) {
        List<User> students = orthoService.getAllStudents(orthoId);
        return ResponseEntity.ok(students);
    }

    // Ajouter un patient à l'orthophoniste via la table de lien
    @PostMapping("/patient/ajouter")
    public ResponseEntity<?> addStudent(@RequestParam String orthoId, @RequestParam String studentId) {
        try {
            // Vérifier si le lien existe déjà
            if (orthoService.linkExists(orthoId, studentId)) {
                return ResponseEntity.status(400).body("Cet élève est déjà associé à cet orthophoniste.");
            }
            Link link = orthoService.addStudentToOrtho(orthoId, studentId);
            return ResponseEntity.ok(link);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }

    // Vérifier si un lien existe entre l'orthophoniste et un patient
    @GetMapping("/patient/lien")
    public ResponseEntity<?> checkLink(@RequestParam String orthoId, @RequestParam String studentId) {
        boolean exists = orthoService.linkExists(orthoId, studentId);
        return ResponseEntity.ok(exists);
    }
}

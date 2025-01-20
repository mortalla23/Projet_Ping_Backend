package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.service.OrthoService;
import fr.esigelec.ping.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/api/orthophoniste")
@CrossOrigin(origins = "http://localhost:3000") // Permettre l'accès depuis React.js
public class OrthoController {

    @Autowired
    private OrthoService orthoService;

    @Autowired
    private UserService userService;

    // Récupérer tous les patients associés à l'orthophoniste
    @GetMapping("/patients")
    public ResponseEntity<?> getAllStudents(
            @RequestParam(defaultValue = "0") int orthoId, 
            @RequestParam(required = false) String email) {
    
        // Cas 1 : Recherche par orthoId
        if (orthoId != 0) {
            List<User> students = orthoService.getAllStudents(orthoId);
            if (students == null || students.isEmpty()) {
                return ResponseEntity.status(404).body("Aucun patient trouvé pour cet orthophoniste.");
            }
            return ResponseEntity.ok(students);
        }
    
        // Cas 2 : Recherche par email
        if (email != null) {
            System.out.println("Recherche par email pour : " + email); // Log pour l'email
            User student = userService.findStudentByEmail(email);
            if (student == null) {
                return ResponseEntity.status(404).body("Aucun élève trouvé avec cet email.");
            }
            return ResponseEntity.ok(student);
        }
    
        // Si aucun paramètre valide n'est fourni
        return ResponseEntity.status(400).body("Paramètres invalides : veuillez fournir un orthoId ou un email.");
    }
    

    // Ajouter un patient à l'orthophoniste via la table de lien
    @PostMapping("/patient/ajouter")
    public ResponseEntity<?> addStudent(@RequestParam int orthoId, @RequestParam int studentId) {
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
    public ResponseEntity<?> checkLink(@RequestParam int orthoId, @RequestParam int studentId) {
        boolean exists = orthoService.linkExists(orthoId, studentId);
        return ResponseEntity.ok(exists);
    }
}

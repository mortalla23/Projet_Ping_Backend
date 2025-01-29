package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.service.OrthoService;
import fr.esigelec.ping.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orthophoniste")
@CrossOrigin(origins = "http://localhost:3000") // Autoriser les requêtes depuis React.js
public class OrthoController {

    @Autowired
    private OrthoService orthoService;

    @Autowired
    private UserService userService;

    /**
     * Récupérer tous les patients associés à un orthophoniste donné.
     *
     * @param data Données contenant l'ID de l'orthophoniste
     * @return Liste des patients associés ou message d'erreur
     */
    @PostMapping("/patients")
    public ResponseEntity<?> getAllPatients(@RequestBody Map<String, Integer> data) {
        System.out.println("Requête reçue avec les données : " + data);

        try {
            // Vérifier si "orthoId" est fourni dans la requête
            if (!data.containsKey("orthoId")) {
                return ResponseEntity.badRequest().body("Erreur : 'orthoId' est requis.");
            }

            int orthoId = data.get("orthoId");

            // Récupérer les patients liés à l'orthophoniste
            List<User> patients = orthoService.getAllStudents(orthoId);

            // Si aucun patient trouvé, renvoyer un message d'erreur
            if (patients == null || patients.isEmpty()) {
                return ResponseEntity.status(404).body("Aucun patient trouvé pour cet orthophoniste.");
            }

            return ResponseEntity.ok(patients);

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des patients : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }

    /**
     * Récupérer tous les patients validés associés à un orthophoniste donné.
     *
     * @param orthoId ID de l'orthophoniste
     * @return Liste des patients validés ou message d'erreur
     */
    @GetMapping("/patients/validated")
    public ResponseEntity<?> getValidatedPatients(@RequestParam int orthoId) {
        System.out.println("Récupération des patients validés pour l'orthophoniste ID : " + orthoId);

        try {
            // Récupérer les patients validés pour cet orthophoniste
            List<User> validatedPatients = orthoService.getValidatedPatients(orthoId);

            // Si aucun patient validé trouvé
            if (validatedPatients == null || validatedPatients.isEmpty()) {
                return ResponseEntity.status(404).body("Aucun patient validé trouvé pour cet orthophoniste.");
            }

            return ResponseEntity.ok(validatedPatients);

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des patients validés : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }

    /**
     * Ajouter un patient à l'orthophoniste via la table de lien.
     *
     * @param orthoId   ID de l'orthophoniste
     * @param studentId ID du patient
     * @return Le lien créé ou message d'erreur
     */
    @PostMapping("/patient/ajouter")
    public ResponseEntity<?> addPatient(@RequestParam int orthoId, @RequestParam int studentId) {
        try {
            // Vérifier si le lien existe déjà
            if (orthoService.linkExists(orthoId, studentId)) {
                return ResponseEntity.badRequest().body("Cet élève est déjà associé à cet orthophoniste.");
            }

            // Ajouter le lien
            Link link = orthoService.addStudentToOrtho(orthoId, studentId);
            return ResponseEntity.ok(link);

        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout du patient : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }

    /**
     * Vérifier si un lien existe entre un orthophoniste et un patient.
     *
     * @param orthoId   ID de l'orthophoniste
     * @param studentId ID du patient
     * @return Vrai si le lien existe, faux sinon
     */
    @GetMapping("/patient/lien")
    public ResponseEntity<?> checkLink(@RequestParam int orthoId, @RequestParam int studentId) {
        try {
            boolean exists = orthoService.linkExists(orthoId, studentId);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification du lien : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }
}

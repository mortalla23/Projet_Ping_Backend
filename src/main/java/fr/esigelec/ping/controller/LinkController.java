package fr.esigelec.ping.controller;
import java.util.Collections;
import java.util.List;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.Teacher;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.repository.UserRepository;
import fr.esigelec.ping.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserRepository userRepository;

    
    /**
     * Crée un lien entre un orthophoniste et un patient.
     *
     * @param data Map contenant l'ID de l'orthophoniste (orthoId) et l'ID du patient (patientId)
     * @return Le lien créé ou un message d'erreur
     */
   
    @PostMapping("/create")
    public ResponseEntity<?> createLink(@RequestBody Map<String, Object> data) {
        System.out.println("Requête reçue dans createLink avec les données : " + data);

        // Check for required fields
        if (!data.containsKey("orthoId") && !data.containsKey("teacherId") || !data.containsKey("patientId")) {
            return ResponseEntity.badRequest().body("Données manquantes ou invalides.");
        }

        try {
            int linkerId;
            String role;
            
            // Vérification si c'est un lien avec un orthophoniste ou un enseignant
            if (data.containsKey("orthoId")) {
                linkerId = (int) data.get("orthoId");
                role = "ORTHOPHONIST"; // Rôle pour orthophoniste
            } else if (data.containsKey("teacherId")) {
                linkerId = (int) data.get("teacherId");
                role = "TEACHER"; // Rôle pour enseignant
            } else {
                return ResponseEntity.badRequest().body("Aucun ID d'orthophoniste ou d'enseignant trouvé.");
            }
            
            int linkedTo = (int) data.get("patientId"); // Utilisation de patientId pour le lien
            String validationStatus = "ONGOING";  // Statut de validation par défaut

            // Validation des IDs
            if (linkerId <= 0 || linkedTo <= 0) {
                return ResponseEntity.badRequest().body("Données invalides.");
            }

            LinkValidation validate = LinkValidation.valueOf(validationStatus);
            Link link = linkService.createLink(linkerId, linkedTo, validate, role);
            link.setRole(role); // Définir le rôle du lien (orthophoniste ou enseignant)

            System.out.println("Lien créé avec succès : " + link);
            return ResponseEntity.ok(link);
        } catch (Exception ex) {
            System.err.println("Erreur inattendue : " + ex.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }


    /**
     * Met à jour le statut de validation d'un lien.
     *
     * @param linkId ID du lien à mettre à jour
     * @param data   Map contenant le statut de validation (VALIDATED, REFUSED)
     * @return Message de succès ou d'erreur
     */
    @PatchMapping("/{linkId}/validate")
    public ResponseEntity<String> updateLinkValidation(@PathVariable("linkId") String linkId, @RequestBody Map<String, String> data) {
        try {
            // Récupérer le statut à partir de l'objet JSON
            String status = data.get("status");
            if (status == null) {
                return ResponseEntity.badRequest().body("Le statut est manquant dans la requête.");
            }

            // Vérification des données
            System.out.println("Statut reçu : '" + status + "'");

            // Assurez-vous que le statut est une valeur valide de LinkValidation
            try {
                LinkValidation linkValidationStatus = LinkValidation.valueOf(status.toUpperCase().trim());
                linkService.updateValidationStatus(linkId, linkValidationStatus);  
                return ResponseEntity.ok("Statut mis à jour avec succès");
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Statut invalide fourni.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour du statut.");
        }
    }



    /**
     * Récupère tous les liens pour un orthophoniste donné.
     *
     * @param orthoId ID de l'orthophoniste
     * @return Liste des liens associés
     */
    @GetMapping("/orthophoniste/{orthoId}")
    public ResponseEntity<?> getLinksByOrthophonist(@PathVariable int orthoId) {
        try {
            return ResponseEntity.ok(linkService.getStudentsByTeacherId(orthoId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la récupération des liens.");
        }
    }

    /**
     * Récupère tous les liens pour un patient donné.
     *
     * @param patientId ID du patient
     * @return Liste des liens associés
     */
    /**
     * Récupère tous les liens associés à un patient donné.
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getLinksByPatient(@PathVariable int patientId) {
        try {
            List<Link> links = linkService.getLinksByPatientId(patientId);
            
            // Si les liens contiennent des informations sur l'orthophoniste, retourne-les
            if (links != null && !links.isEmpty()) {
                return ResponseEntity.ok(links);
            } else {
                return ResponseEntity.status(404).body("Aucun lien trouvé pour ce patient.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la récupération des liens.");
        }
    }

 
    
    /**
     * Supprime un lien.
     *
     * @param linkId ID du lien à supprimer
     * @return Message de succès ou d'erreur
     */
    @DeleteMapping("/{linkId}")
    public ResponseEntity<?> deleteLink(@PathVariable String linkId) {
        try {
            linkService.deleteLink(linkId);
            return ResponseEntity.ok("Lien supprimé avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la suppression du lien.");
        }
    }
    
    
    /**
     * Récupère tous les liens validés pour un orthophoniste donné.
     *
     * @param orthoId ID de l'orthophoniste
     * @return Liste des liens validés associés
     */
    @PostMapping("/validated")
    public ResponseEntity<?> getValidatedLinks(@RequestBody Map<String, Integer> requestData) {
        Integer linkerId = requestData.get("linkerId"); // Récupérer l'ID de l'orthophoniste

        System.out.println("📥 Paramètre linkerId reçu dans le backend : " + linkerId);

        // Vérification de l'ID reçu
        if (linkerId == null || linkerId <= 0) {
            return ResponseEntity.badRequest().body("❌ Le paramètre 'linkerId' est requis et doit être un entier valide.");
        }

        try {
            // 🔹 Récupère uniquement les liens validés
            List<Link> validatedLinks = linkService.getValidatedLinks(linkerId)
                    .stream()
                    .filter(link -> "VALIDATED".equals(link.getValidate().name())) // 👈 Filtre uniquement les VALIDATED
                    .collect(Collectors.toList());

            if (validatedLinks.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList()); // Retourne une liste vide si aucun patient validé
            }

            System.out.println("✅ Liens validés trouvés : " + validatedLinks.size());
            return ResponseEntity.ok(validatedLinks);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des liens validés : " + e.getMessage());
            return ResponseEntity.status(500).body("🚨 Erreur interne du serveur.");
        }
    }


    @PostMapping("/details")
    public ResponseEntity<?> getPatientDetails(@RequestBody List<Integer> patientIds) {
        if (patientIds == null || patientIds.isEmpty()) {
            return ResponseEntity.badRequest().body("La liste des IDs des patients est vide.");
        }

        try {
            // Logique pour traiter les patientIds
            List<User> patients = userRepository.findAllById(patientIds);
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des détails des patients : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }


    @GetMapping("/teachers")
    public ResponseEntity<?> getTeachersForPatients(@RequestParam List<Integer> patientIds) {
        try {
            List<Teacher> teachers = linkService.getTeachersByPatientIds(patientIds);
            return ResponseEntity.ok(teachers);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des enseignants : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur lors de la récupération des enseignants.");
        }
    }



}

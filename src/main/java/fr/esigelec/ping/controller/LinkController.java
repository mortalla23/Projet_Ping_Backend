package fr.esigelec.ping.controller;
import java.util.Collections;
import java.util.List;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.Teacher;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;
import fr.esigelec.ping.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinkRepository linkRepository;

    
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
        if (!data.containsKey("linkerId") || !data.containsKey("linkedTo")) {
            return ResponseEntity.badRequest().body("Données manquantes ou invalides.");
        }

        try {
            int linkerId = (int) data.get("linkerId");      // Use orthoId as linkerId
            int linkedTo = (int) data.get("linkedTo");    // Use patientId as linkedTo
            String validationStatus = "ONGOING";           // Default validation status
            String role = (String)data.get("role");                 // Default role

            // Additional validation
            if (linkerId <= 0 || linkedTo <= 0) {
                return ResponseEntity.badRequest().body("Données invalides.");
            }

            LinkValidation validate = LinkValidation.valueOf(validationStatus);
            Link link = linkService.createLink(linkerId, linkedTo, validate, role);
            link.setRole(role); // Add role to the link
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
    /*  @PatchMapping("/{linkId}/validate")
    public ResponseEntity<?> updateLinkValidation(
            @PathVariable int linkId,
            @RequestBody Map<String, String> data
    ) {
        String validationStatus = data.get("status"); // "VALIDATED" ou "REFUSED"

        if (!LinkValidation.isValidRole(validationStatus)) {
            return ResponseEntity.badRequest().body("Statut invalide.");
        }

        try {
            linkService.updateValidationStatus(String.valueOf(linkId), LinkValidation.valueOf(validationStatus));
            return ResponseEntity.ok("Statut mis à jour avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/

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
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<?> getLinksByPatient(@PathVariable int patientId) {
        try {
            return ResponseEntity.ok(linkService.getLinksByPatientId(patientId));
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
    public ResponseEntity<?> getValidatedLinks(@RequestBody Map<String, String> requestData) {
        Integer linkerId = Integer.parseInt(requestData.get("linkerId")); // Récupérer l'ID de l'orthophoniste

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

    @PostMapping("/rejected")
    public ResponseEntity<?> getRejectedLinks(@RequestBody Map<String, String> requestData) {
        Integer linkerId = Integer.parseInt(requestData.get("linkerId")); // Récupérer l'ID de l'orthophoniste

        System.out.println("📥 Paramètre linkerId reçu dans le backend : " + linkerId);

        // Vérification de l'ID reçu
        if (linkerId == null || linkerId <= 0) {
            return ResponseEntity.badRequest().body("❌ Le paramètre 'linkerId' est requis et doit être un entier valide.");
        }

        try {
            // 🔹 Récupère uniquement les liens validés
            List<Link> rejectedLinks = linkService.getValidatedLinks(linkerId)
                    .stream()
                    .filter(link -> "REFUSED".equals(link.getValidate().name())) // 👈 Filtre uniquement les VALIDATED
                    .collect(Collectors.toList());

            System.out.println("Links: "+ rejectedLinks);

            if (rejectedLinks.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList()); // Retourne une liste vide si aucun patient validé
            }

            System.out.println("✅ Liens validés trouvés : " + rejectedLinks.size());
            return ResponseEntity.ok(rejectedLinks);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des liens validés : " + e.getMessage());
            return ResponseEntity.status(500).body("🚨 Erreur interne du serveur.");
        }
    }

    

    @GetMapping("/ongoing")
    public ResponseEntity<List<Link>> getOngoingLinks() {
        List<Link> ongoingLinks = linkService.getOngoingLinks();
        return ResponseEntity.ok(ongoingLinks);
    }

    
    @PostMapping("/validate")
    public ResponseEntity<?> validateLink(@RequestParam("linkId") String linkId) {
        System.out.println("📌 Requête reçue - ID du lien: " + linkId);

        if (linkId == null || linkId.isEmpty()) {
            return ResponseEntity.badRequest().body("Erreur: ID du lien manquant");
        }

        boolean success = linkService.validateLink(linkId);

        if (!success) {
            return ResponseEntity.status(404).body("Erreur: Lien introuvable");
        }

        return ResponseEntity.ok("Lien validé avec succès !");
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectLink(@RequestParam("linkId") String linkId) {
        System.out.println("📌 Requête reçue - ID du lien: " + linkId);

        if (linkId == null || linkId.isEmpty()) {
            return ResponseEntity.badRequest().body("Erreur: ID du lien manquant");
        }

        boolean success = linkService.rejectLink(linkId);

        if (!success) {
            return ResponseEntity.status(404).body("Erreur: Lien introuvable");
        }

        return ResponseEntity.ok("Lien validé avec succès !");
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

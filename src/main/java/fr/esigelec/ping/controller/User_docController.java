package fr.esigelec.ping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.esigelec.ping.model.UserDocument;
import fr.esigelec.ping.model.UserDocumentRequest;
import fr.esigelec.ping.service.UserDocumentService;

@RestController
@RequestMapping("/api/user-documents")
@CrossOrigin(origins = "http://localhost:3000") // Permet l'accès depuis le frontend
public class User_docController {

    @Autowired
    private UserDocumentService documentService;

    // Endpoint pour créer ou mettre à jour un document utilisateur
    @PostMapping
    public ResponseEntity<?> createOrUpdateDocument(@RequestBody UserDocumentRequest documentRequest) {
        try {
            // Validation de la requête
            if (documentRequest.getUserId() == null || documentRequest.getUserId().isEmpty()) {
                return ResponseEntity.badRequest().body("Le champ 'userId' est requis.");
            }
            if (documentRequest.getDocumentName() == null || documentRequest.getDocumentName().isEmpty()) {
                return ResponseEntity.badRequest().body("Le champ 'documentName' est requis.");
            }

            // Conversion en UserDocument
            UserDocument document = documentService.fromRequest(documentRequest);

            // Appelle le service pour créer ou mettre à jour le document
            documentService.createOrUpdateDocument(document);

            return ResponseEntity.ok("Document enregistré avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'enregistrement du document : " + e.getMessage());
        }
    }


    // Endpoint pour récupérer les documents d'un utilisateur
    @GetMapping
    public ResponseEntity<?> getDocumentsForUser(
        @RequestParam String userId,
        @RequestParam(required = false) String documentType
    ) {
        try {
            // Validation de l'utilisateur
            if (userId == null || userId.isEmpty()) {
                return ResponseEntity.badRequest().body("Le champ 'userId' est requis.");
            }

            // Appelle le service pour récupérer les documents
            List<UserDocument> documents = documentService.getDocumentsForUser(userId, documentType);

            if (documents.isEmpty()) {
                return ResponseEntity.ok("Aucun document trouvé pour l'utilisateur.");
            }

            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la récupération des documents : " + e.getMessage());
        }
    }
}

package fr.esigelec.ping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import fr.esigelec.ping.model.UserDocument;
import fr.esigelec.ping.model.UserDocumentRequest;
import fr.esigelec.ping.service.UserDocumentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user-documents")
@CrossOrigin(origins = "http://localhost:3000") // Permet l'accès depuis le frontend
public class User_docController {

    @Autowired
    private UserDocumentService documentService;

    // Endpoint pour créer ou mettre à jour un document utilisateur
    @PostMapping
    public ResponseEntity<?> createOrUpdateDocument(@RequestBody @Valid UserDocumentRequest documentRequest) {
        try {
            System.out.println(documentRequest);
            // Validation de la requête
            if (documentRequest.getUserId() == 0 ) {
                return ResponseEntity.badRequest().body("Le champ 'userId' est requis.");
            }
            if (documentRequest.getDocumentType() == null || documentRequest.getDocumentType().isEmpty()) {
                return ResponseEntity.badRequest().body("Le champ 'documentType' est requis.");
            }
            if (documentRequest.getDocumentId() == 0) {
                return ResponseEntity.badRequest().body("Le champ 'documentId' est requis.");
            }

            // Conversion en UserDocument

            UserDocument document = documentService.fromRequest(documentRequest);
            documentService.createOrUpdateDocument(document);
            return ResponseEntity.ok("Document xenregistré avec succès.");
        } catch (MethodArgumentNotValidException e) {
            return ResponseEntity.badRequest().body("Erreur de validation : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'enregistrement du document : " + e.getMessage());
        }
    }



    // Endpoint pour récupérer les documents d'un utilisateur
    @GetMapping
    public ResponseEntity<?> getDocumentsForUser(
        @RequestParam int userId,
        @RequestParam(required = false) String documentType
    ) {
        try {
            // Validation de l'utilisateur
            if (userId == 0) {
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
    
    /*@GetMapping("/{userId}")
    public ResponseEntity<List<UserDocument>> getUserDocuments(@PathVariable String userId,
                                                                @RequestParam(required = false) String documentType) {
        List<UserDocument> documents = documentService.getDocumentsForUser(userId, documentType); // Utilisation correcte de l'instance
        return ResponseEntity.ok(documents);
    }


    @DeleteMapping("/{documentId}")
    public ResponseEntity<Void> deleteUserDocument(@PathVariable String documentId) {
        documentService.deleteDocument(documentId);  // Utilisation correcte de l'instance
        return ResponseEntity.noContent().build();
    }*/

}

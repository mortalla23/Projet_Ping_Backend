package fr.esigelec.ping.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.esigelec.ping.model.UserDocument;
import fr.esigelec.ping.model.UserDocumentRequest;
import fr.esigelec.ping.repository.UserDocRepository;
import java.time.LocalDateTime;


@Service
public class UserDocumentService {

    @Autowired
    private UserDocRepository documentRepository;

    /**
     * Créer ou mettre à jour un document utilisateur
     */
    
    public UserDocument createOrUpdateDocument(UserDocument document) {
        // Ajouter ou mettre à jour les champs nécessaires
        System.out.print(document);
        if (document.getId() == 0) { // Si c'est une création
            document.setCreatedAt(LocalDateTime.now());
            document.setId(generateUniqueUserId());
        }
        else {
            documentRepository.deleteById(document.getId());
            
        }
                document.setUpdatedAt(LocalDateTime.now());  // Utiliser Date au lieu de LocalDateTime
        // Enregistrer le document
        return documentRepository.save(document);
    }
    public UserDocument fromRequest(UserDocumentRequest request) {
        UserDocument document = new UserDocument();
        document.setId(request.getId());
        document.setUserId(request.getUserId());
        document.setDocumentId(request.getDocumentId());
        document.setDocumentName(request.getDocumentName());
        document.setDocumentType(request.getDocumentType());
        document.setIsPublic(request.getIsPublic());
       // document.setUpdatedBy(request.getUpdatedBy());
        return document;
    }

    /**
     * Récupérer les documents pour un utilisateur, optionnellement filtrés par type
     */
    public List<UserDocument> getDocumentsForUser(int userId, String documentType) {
        if (documentType != null && !documentType.isEmpty()) {
            return documentRepository.findByUserIdAndDocumentType(userId, documentType);
        }
        return documentRepository.findByUserId(userId);
    }

   

    /**
     * Supprimer un document par son ID
     */
    public void deleteDocument(int documentId) {
        if (!documentRepository.existsById(documentId)) {
            throw new IllegalArgumentException("Document introuvable : " + documentId);
        }
        documentRepository.deleteById(documentId);
    }

    // 🔄 Génération d'un ID unique pour le message
    private int generateUniqueUserId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (documentRepository.existsById(id));
        return id;
    }
    
}

package fr.esigelec.ping.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.esigelec.ping.model.UserDocument;
import fr.esigelec.ping.model.UserDocumentRequest;
import fr.esigelec.ping.repository.UserDocRepository;


@Service
public class UserDocumentService {

    @Autowired
    private UserDocRepository documentRepository;

    /**
     * Créer ou mettre à jour un document utilisateur
     */
    public UserDocument createOrUpdateDocument(UserDocument document) {
        // Ajouter ou mettre à jour les champs nécessaires
        if (document.getId() == null) { // Si c'est une création
            document.setCreatedAt(LocalDateTime.now());
        }
        document.setUpdatedAt(LocalDateTime.now());

        // Enregistrer le document
        return documentRepository.save(document);
    }
    public UserDocument fromRequest(UserDocumentRequest request) {
        UserDocument document = new UserDocument();
        document.setUserId(request.getUserId());
        document.setDocumentName(request.getDocumentName());
        document.setDocumentType(request.getDocumentType());
        document.setIsPublic(request.getIsPublic());
        //document.setUpdatedBy(request.getUpdatedBy());
        return document;
    }

    /**
     * Récupérer les documents pour un utilisateur, optionnellement filtrés par type
     */
    public List<UserDocument> getDocumentsForUser(String userId, String documentType) {
        if (documentType != null && !documentType.isEmpty()) {
            return documentRepository.findByUserIdAndDocumentType(userId, documentType);
        }
        return documentRepository.findByUserId(userId);
    }


    /**
     * Supprimer un document par son ID
     */
    public void deleteDocument(String documentId) {
        if (!documentRepository.existsById(documentId)) {
            throw new IllegalArgumentException("Document introuvable : " + documentId);
        }
        documentRepository.deleteById(documentId);
    }
}

package fr.esigelec.ping.config;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.lang.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.service.LinkService;
import fr.esigelec.ping.service.UserDocumentService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("customAuthorizationManager")
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final LinkService linkService;
    private final UserDocumentService userDocumentService;
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthorizationManager.class);

    public CustomAuthorizationManager(LinkService linkService, UserDocumentService userDocumentService) {
        this.linkService = linkService;
        this.userDocumentService = userDocumentService;
    }

    @Override
    @Nullable
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();
        logger.info("Traitement de la demande pour l'URI: {}", requestURI);
        boolean hasRequiredRole=false;
        
        String currentUserIdStr = (String) authentication.get().getPrincipal();
        int currentUserId = Integer.parseInt(currentUserIdStr); // Assurez-vous que c'est un String
    
      if (requestURI.startsWith("/api/user-documents")){

        
        logger.info("Traitement de la demande pour l'URI: {}", requestURI);
        
        // Extraire userId depuis les paramètres de requête
        String userIdFromParam = request.getParameter("userId");
        String documentTypeFromParam = request.getParameter("documentType");
       
        if (userIdFromParam == null) {
            logger.error("userId non spécifié dans la requête");
            return new AuthorizationDecision(false); // Accès refusé
        }
    
        int userIdFromURL;
        try {
            userIdFromURL = Integer.parseInt(userIdFromParam);
        } catch (NumberFormatException e) {
            logger.error("userId invalide : {}", userIdFromParam);
            return new AuthorizationDecision(false); // Accès refusé
        }
    
        logger.info("Comparaison des utilisateurs: ID depuis les paramètres : {}, ID courant : {}", userIdFromURL, currentUserId);
            if (documentTypeFromParam.equals("HistoriqueSante") || documentTypeFromParam.equals("Anamnese")){
        // Vérifier si l'utilisateur a les rôles nécessaires
         hasRequiredRole = authentication.get().getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("STUDENT") ||
                                           grantedAuthority.getAuthority().equals("ORTHOPHONIST") ||
                                           grantedAuthority.getAuthority().equals("PATIENT"));
            
        // Vérifier les autorisations
        if (hasRequiredRole) {
            if (currentUserId == userIdFromURL || linkService.isLinkedWith(currentUserId, userIdFromURL, "ORTHOPHONISTE", LinkValidation.VALIDATED) ) {
                return new AuthorizationDecision(true); // Accès accordé
            }
        }
        } else if (documentTypeFromParam.equals("HistoriqueEducation") || documentTypeFromParam.equals("PPRE")){
        
              // Vérifier si l'utilisateur a les rôles nécessaires
         hasRequiredRole = authentication.get().getAuthorities().stream()
         .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("STUDENT") ||
                                        grantedAuthority.getAuthority().equals("ORTHOPHONIST") ||
                                        grantedAuthority.getAuthority().equals("PATIENT") ||
                                        grantedAuthority.getAuthority().equals("ENSEIGNANT") ||
                                        grantedAuthority.getAuthority().equals("TEACHER"));
         
     // Vérifier les autorisations
     if (hasRequiredRole) {

         if (currentUserId == userIdFromURL || linkService.isLinkedWith(currentUserId, userIdFromURL, "ORTHOPHONISTE", LinkValidation.VALIDATED)|| linkService.isLinkedWith(currentUserId, userIdFromURL, "TEACHER", LinkValidation.VALIDATED) || linkService.isLinkedWith(currentUserId, userIdFromURL, "ENSEIGANT", LinkValidation.VALIDATED)) {
             return new AuthorizationDecision(true); // Accès accordé
         }
     }
        
        }
        
    
        logger.info("Accès refusé à l'utilisateur avec ID : {}", currentUserId);
        return new AuthorizationDecision(false); // Accès refusé
    } else  if ( requestURI.startsWith("/api/historique-sante") || requestURI.startsWith("/api/anamnese")){

        logger.info("Traitement de la demande pour l'URI: {}", requestURI);
        
        // Extraire userId depuis les paramètres de requête
        String docIdFromParam = requestURI.split("/")[3]; // Récupérer l'ID utilisateur d
        if (docIdFromParam == null) {
            logger.error("userId non spécifié dans la requête");
            return new AuthorizationDecision(false); // Accès refusé
        }
    
        int docIdFromURL;
        try {
            docIdFromURL = Integer.parseInt(docIdFromParam);
        } catch (NumberFormatException e) {
            logger.error("docId invalide : {}", docIdFromParam);
            return new AuthorizationDecision(false); // Accès refusé
        }
    
        
        logger.info("Vérification du docId : {}, ID courant : {}", docIdFromURL, currentUserId);
           // Vérifier si l'utilisateur a les rôles nécessaires
         hasRequiredRole = authentication.get().getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("STUDENT") ||
                                           grantedAuthority.getAuthority().equals("ORTHOPHONIST") ||
                                           grantedAuthority.getAuthority().equals("PATIENT"));
            
        // Vérifier les autorisations
        if (hasRequiredRole) {

            Optional<Integer> userIdOptional = userDocumentService.getUserIdByDocumentId(docIdFromURL);
            int userIdFromURL;
            if (userIdOptional.isPresent()) {
                userIdFromURL = userIdOptional.get();
                // Utilisez userIdFromURL selon vos besoins
                System.out.println("User ID trouvé : " + userIdFromURL);
            } else {
               userIdFromURL=0;
             }
             if (currentUserId == userIdFromURL || linkService.isLinkedWith(currentUserId, userIdFromURL, "ORTHOPHONISTE", LinkValidation.VALIDATED) ) {
                return new AuthorizationDecision(true); // Accès accordé
            }
        }
    }
        else if (requestURI.startsWith("/api/ppre") || requestURI.startsWith("/api/pap") || requestURI.startsWith("/api/historique-education")){

            logger.info("Traitement de la demande pour l'URI: {}", requestURI);
        
        // Extraire userId depuis les paramètres de requête
        String docIdFromParam = requestURI.split("/")[3]; // Récupérer l'ID utilisateur d
        if (docIdFromParam == null) {
            logger.error("userId non spécifié dans la requête");
            return new AuthorizationDecision(false); // Accès refusé
        }
    
        int docIdFromURL;
        try {
            docIdFromURL = Integer.parseInt(docIdFromParam);
        } catch (NumberFormatException e) {
            logger.error("docId invalide : {}", docIdFromParam);
            return new AuthorizationDecision(false); // Accès refusé
        }
        logger.info("Vérification du docId : {}", docIdFromURL);
       
                 // Vérifier si l'utilisateur a les rôles nécessaires
         hasRequiredRole = authentication.get().getAuthorities().stream()
         .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("STUDENT") ||
                                        grantedAuthority.getAuthority().equals("ORTHOPHONIST") ||
                                        grantedAuthority.getAuthority().equals("PATIENT") ||
                                        grantedAuthority.getAuthority().equals("ENSEIGNANT") ||
                                        grantedAuthority.getAuthority().equals("TEACHER"));
         
      // Vérifier les autorisations
      if (hasRequiredRole) {

        Optional<Integer> userIdOptional = userDocumentService.getUserIdByDocumentId(docIdFromURL);
        int userIdFromURL;
        if (userIdOptional.isPresent()) {
            userIdFromURL = userIdOptional.get();
            // Utilisez userIdFromURL selon vos besoins
            System.out.println("User ID trouvé : " + userIdFromURL);
        } else {
           userIdFromURL=0;
         }
         if (currentUserId == userIdFromURL || linkService.isLinkedWith(currentUserId, userIdFromURL, "ORTHOPHONISTE", LinkValidation.VALIDATED) ) {
            return new AuthorizationDecision(true); // Accès accordé
        }
    }
        }
        
    
        logger.info("Accès refusé à l'utilisateur avec ID : {}", currentUserId);
        return new AuthorizationDecision(false); // Accès refusé
    }

}

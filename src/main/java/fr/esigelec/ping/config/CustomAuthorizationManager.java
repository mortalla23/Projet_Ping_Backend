package fr.esigelec.ping.config;

import java.util.function.Supplier;

import org.springframework.lang.Nullable;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.service.LinkService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("customAuthorizationManager")
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final LinkService linkService;
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthorizationManager.class);

    public CustomAuthorizationManager(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    @Nullable
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context) {
        HttpServletRequest request = context.getRequest();
        String requestURI = request.getRequestURI();
        logger.info("Traitement de la demande pour l'URI: {}", requestURI);
    
        int userIdFromURL = Integer.parseInt(requestURI.split("/")[3]); // Récupérer l'ID utilisateur de l'URL
        String currentUserIdStr = (String) authentication.get().getPrincipal(); // Assurez-vous que c'est un String
        int currentUserId = Integer.parseInt(currentUserIdStr); // Convertir en int
    
        logger.info("Comparaison des utilisateurs: ID depuis l'URL: {}, ID courant: {}", userIdFromURL, currentUserId);
    
        // Vérifier si l'utilisateur a les rôles nécessaires
        boolean hasRequiredRole = authentication.get().getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("STUDENT") ||
                                           grantedAuthority.getAuthority().equals("ORTHOPHONIST") ||
                                           grantedAuthority.getAuthority().equals("PATIENT"));
    
        // Vérifier les autorisations
        if (hasRequiredRole) {
            if (currentUserId == userIdFromURL || linkService.isLinkedWith(currentUserId, userIdFromURL, "ORTHOPHONIST", LinkValidation.VALIDATED)) {
                return new AuthorizationDecision(true); // Accès accordé
            }
        }
    
        logger.info("Accès refusé à l'utilisateur avec ID: {}", currentUserId);
        return new AuthorizationDecision(false); // Accès refusé
    }
   
}

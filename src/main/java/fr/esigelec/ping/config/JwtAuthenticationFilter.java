package fr.esigelec.ping.config;

import fr.esigelec.ping.model.JwtUtil;
import fr.esigelec.ping.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                
        String token = extractTokenFromHeader(request);
        logger.info("📌 Le token reçu : " + token);
        logger.info("Le token est valide?");
        if (token != null && JwtUtil.validateToken(token)) {
            String userId = String.valueOf(JwtUtil.getIdFromToken(token)); // 🔹 Convertir ID en String
          
         logger.info("📌 ID extrait du token : " + userId); // 🔹 Affichage dans les logs
            Role role = JwtUtil.extractRole(token);
            logger.info("Le token est validé");
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role.name()));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
                        
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Contexte de sécurité configuré : {}"+ SecurityContextHolder.getContext().getAuthentication());
        }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        logger.info("Le token"+ header);
        if (header != null && header.startsWith("Bearer ")) {
            logger.info("Juqu'ici cest bon"+header.substring(7));
            return header.substring(7);
        }
        return null;
    }
}

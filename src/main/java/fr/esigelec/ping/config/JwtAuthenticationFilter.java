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
                if (request.getRequestURI().equals("/api/users/connexion")) {
    filterChain.doFilter(request, response);
    return;
}       
        if (request.getRequestURI().equals("/ws") || request.getRequestURI().equals("/ws2")) {
            logger.info("Traitement de la demande pour l'URI: {}"+ request.getRequestURI());
    filterChain.doFilter(request, response);
    return;
}
        String token = extractTokenFromHeader(request);
        if (token != null && JwtUtil.validateToken(token)) {
            String userId = String.valueOf(JwtUtil.getIdFromToken(token)); // 🔹 Convertir ID en String
          
         logger.info("📌 ID extrait du token : " + userId); // 🔹 Affichage dans les logs
            Role role = JwtUtil.extractRole(token);
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role.name()));

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, authorities);
                        
            SecurityContextHolder.getContext().setAuthentication(authentication);
             }

        filterChain.doFilter(request, response);
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
             return header.substring(7);
        }
        return null;
    }
}
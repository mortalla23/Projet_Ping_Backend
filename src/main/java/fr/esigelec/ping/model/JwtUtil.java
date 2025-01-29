package fr.esigelec.ping.model;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import fr.esigelec.ping.model.enums.Role;;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "supersecretkeyforsigningjwtmustbeatleast32byteslong"; // Doit être >= 32 caractères
    private static final long EXPIRATION_TIME = 86400000; // 1 jour en ms

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(int id, Role role) {
        return Jwts.builder()
            .subject(String.valueOf(id))
            .claim("role", role)  
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key)
            .compact();
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    public static Role extractRole(String token) {
        Claims claims = extractClaims(token);
        String roleName = claims.get("role", String.class); // 🔹 Extraire en String
        return Role.valueOf(roleName); // 🔹 Convertir en Enum
    }
    

    // Méthode pour extraire les claims à partir du token
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                   .setSigningKey(key)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }


    public static Integer getIdFromToken(String token) {
        Claims claims = extractClaims(token);
        int id = Integer.parseInt(claims.getSubject());  // Exemple pour récupérer un claim spécifique        
        return id;
    }

    // Méthode pour vérifier si le token est expiré
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new java.util.Date());
    }
}


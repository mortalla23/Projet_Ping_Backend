package fr.esigelec.ping.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.esigelec.ping.model.LinkRequest;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.Role;
import fr.esigelec.ping.service.UserService;
import fr.esigelec.ping.service.LinkService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") // Permettre l'accès depuis React.js
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private LinkService linkService;
    @PostMapping("/connexion")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authentifie l'utilisateur
            User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
            
            // Retourne les détails complets de l'utilisateur
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            // Retourne une erreur 401 si l'authentification échoue
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/inscription")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            System.out.println("Données reçues : " + user);

            // Validation explicite
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                System.out.println("Erreur : Username manquant ou vide");
                return ResponseEntity.badRequest().body("Le champ 'username' est manquant ou invalide.");
            }
            if (user.getEmail() == null || !user.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                System.out.println("Erreur : Email invalide");
                return ResponseEntity.badRequest().body("Le champ 'email' est invalide.");
            }
            if (user.getPassword() == null || user.getPassword().length() < 6) {
                System.out.println("Erreur : Mot de passe trop court");
                return ResponseEntity.badRequest().body("Le mot de passe doit contenir au moins 6 caractères.");
            }
            if (user.getRole() == null || !Role.isValidRole(user.getRole().toString())) {
                System.out.println("Erreur : Rôle invalide");
                throw new IllegalArgumentException("Rôle invalide");
            }


            if (user.getBirthDate() == null || user.getBirthDate().isAfter(LocalDate.now())) {
                System.out.println("Erreur : Date de naissance invalide");
                return ResponseEntity.badRequest().body("Le champ 'birth_date' est invalide.");
            }

            // Enregistrement
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            System.out.println("Erreur serveur : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }
    
   

}

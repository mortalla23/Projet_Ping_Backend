package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.User;
import fr.esigelec.ping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.esigelec.ping.model.enums.Role;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
//@CrossOrigin(origins = "http://127.0.0.1:5200")
public class UserController {

    @Autowired
    private UserService userService;

    // 🔍 Récupérer tous les utilisateurs
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 🔍 Récupérer un utilisateur par ID
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") int userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }


    // 🔐 Inscription d'un utilisateur avec rôle
    @PostMapping("/inscription")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // ✅ Vérifications de base
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                return ResponseEntity.badRequest().body("Le champ 'username' est obligatoire.");
            }

            if (user.getLastName() == null || user.getLastName().isEmpty()) {
                return ResponseEntity.badRequest().body("Le champ 'Nom' est obligatoire.");
            }

            if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
                return ResponseEntity.badRequest().body("Le champ 'Prénom' est obligatoire.");
            }

            if (user.getEmail() == null || !user.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return ResponseEntity.badRequest().body("Le champ 'email' est invalide.");
            }

            if (user.getPassword() == null || user.getPassword().length() < 6) {
                return ResponseEntity.badRequest().body("Le mot de passe doit contenir au moins 6 caractères.");
            }

            if (user.getRole() == null || !Role.isValidRole(user.getRole().name())) {
                return ResponseEntity.badRequest().body("Rôle invalide. Les rôles autorisés sont : USER, ADMIN, MODERATOR.");
            }

            user.setCreatedAt(new Date());

            User savedUser = userService.registerUser(user);

            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }

    
    // 🔐 Endpoint de connexion
    @PostMapping("/connexion")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // ✅ Vérifier les champs obligatoires
            if (loginRequest.getEmail() == null || loginRequest.getEmail().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "Le champ 'email' est obligatoire."));
            }

            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "Le champ 'password' est obligatoire."));
            }

            // 🔎 Vérifier les identifiants
            Optional<User> userOpt = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

            if (userOpt.isPresent()) {
                User user = userOpt.get();

                // ✅ Construire la réponse complète
                Map<String, Object> response = new HashMap<>();
                response.put("id", user.getId());
                response.put("username", user.getUsername());
                response.put("email", user.getEmail());
                response.put("role", user.getRole());
                response.put("message", "Connexion réussie. Bienvenue, " + user.getUsername() + " !");

                return ResponseEntity.ok(response);

            } else {
                return ResponseEntity.status(401)
                        .body(Collections.singletonMap("message", "Email ou mot de passe incorrect."));
            }

        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Collections.singletonMap("message", "Erreur interne du serveur."));
        }
    }

    // ❌ Supprimer un utilisateur
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("{\"message\": \"Utilisateur supprimé avec succès.\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("{\"message\": \"" + e.getMessage() + "\"}");
        }
    }

    // 🔍 Endpoint de recherche progressive par username
    @GetMapping("/search/{username}")
    public ResponseEntity<?> searchUsersByUsername(@PathVariable("username") String username) {
        try {
            List<User> users = userService.searchUsersByUsername(username);

            if (users.isEmpty()) {
                return ResponseEntity.status(404).body(Collections.singletonMap("message", "Aucun utilisateur trouvé."));
            }

            return ResponseEntity.ok(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.singletonMap("message", "Erreur lors de la recherche des utilisateurs."));
        }
    }

    
}

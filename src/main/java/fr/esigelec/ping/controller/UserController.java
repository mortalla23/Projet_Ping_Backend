package fr.esigelec.ping.controller;

import fr.esigelec.ping.model.JwtUtil;
import fr.esigelec.ping.model.LoginRequest;
import fr.esigelec.ping.model.OrthoPatient;
import fr.esigelec.ping.model.Teacher;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.service.TeacherService;
import fr.esigelec.ping.service.TempStorageService;
import fr.esigelec.ping.service.UserService;
import fr.esigelec.ping.service.EmailService;
import fr.esigelec.ping.service.OtpService;
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
    @Autowired
    private TeacherService teacherService; 
    @Autowired
    private OtpService otpService;
    @Autowired
    private TempStorageService tempStorageService;


    @Autowired
    private EmailService emailService;

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

    @PostMapping("/inscription")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Validation logic
            if (user.getPassword().length() < 6) {
                return ResponseEntity.badRequest().body("Le mot de passe doit contenir au moins 6 caractères.");
            }

            if (user.getRole() == null || !Role.isValidRole(user.getRole().name())) {
                return ResponseEntity.badRequest().body("Rôle invalide. Les rôles autorisés sont : USER, ADMIN, MODERATOR.");
            }

            if (userService.existsByEmail(user.getEmail())) {
                return ResponseEntity.badRequest().body("Email déjà utilisé.");
            }
            user.setCreatedAt(new Date());

            // Store user temporarily
            tempStorageService.storeUser(user.getEmail(), user);
            System.out.println("temp set ");
            // Generate OTP
            int otp = otpService.generateOtp(user.getEmail());
            emailService.sendMessage(user.getEmail(), "Registration OTP[BAUMANN]", "Your OTP is: " + otp+ "\n Expiration in 10 min");

            return ResponseEntity.ok("OTP sent to email. Please verify.");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }



     // 🔐 Endpoint de connexion
     @PostMapping("/connexion")
     public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
         try {
            System.out.println("Requête de connexion reçue : "+loginRequest.getPassword());
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
             System.out.println("Requête de service : ");
            Optional<User> userOpt = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

            System.out.println("Requête de service : ");
           
            if (userOpt.isPresent()) {
                User user = userOpt.get();

            
            // Store user temporarily
            tempStorageService.storeUser(user.getEmail(), user);

            // Generate OTP
            int otp = otpService.generateOtp(user.getEmail());
            emailService.sendMessage(user.getEmail(), "Login OTP [BAUMANN]", "Your OTP is: " + otp+ "\n Expiration in 10 min");

            return ResponseEntity.ok("OTP sent to email. Please verify.");
            } else {
                return ResponseEntity.status(401)
                        .body(Collections.singletonMap("message", "Email ou mot de passe incorrect."));
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam int otp) {
        int serverOtp = otpService.getOtp(email);
       System.out.println("serverotp"+serverOtp);
        if (serverOtp == otp) {
            otpService.clearOtp(email);

            // Retrieve user from temporary storage
            User user = tempStorageService.getUser(email);
            if (user != null) {
                // Register or login user
                if (!userService.existsByEmail(email)) {
                    userService.registerUser(user);
                } else {
                    // ✅ Générer le token JWT
                 String token = JwtUtil.generateToken(user.getId(), user.getRole());
                
                // ✅ Construire la réponse complète
                Map<String, Object> response = new HashMap<>();
                response.put("id", user.getId());
                response.put("username", user.getUsername());
                response.put("email", user.getEmail());
                response.put("role", user.getRole());
                response.put("token", token);  // Retourner le token JWT dans la réponse
                response.put("message", "Connexion réussie. Bienvenue, " + user.getUsername() + " !");

                return ResponseEntity.ok(response);

           }
                tempStorageService.removeUser(email);
                emailService.sendMessage(user.getEmail(), "Authentification vérifiée [BAUMANN]", "Votre authentification a été vérifiée avec succès.");

                return ResponseEntity.ok("OTP verified successfully.");
            } else {
                return ResponseEntity.badRequest().body("User not found.");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP.");
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
    
 // Récupérer tous les utilisateurs ayant le rôle "PATIENT"
    @GetMapping("/patients/search")
    public ResponseEntity<List<User>> searchPatients(@RequestParam String searchTerm) {
        try {
            List<User> patients = userService.searchPatients(searchTerm);
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

    
    
    @GetMapping("/patients")
    public ResponseEntity<List<User>> getAllPatients() {
        try {
            List<User> patients = userService.getUsersByRole(Role.PATIENT);
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Collections.emptyList());
        }
    }

    @GetMapping("/patients/sorted")
    public ResponseEntity<List<User>> getAllPatientsSorted() {
        List<User> sortedPatients = userService.getAllPatientsSorted();
        return ResponseEntity.ok(sortedPatients);
    }


    
    
    @GetMapping("/intervenants/{patientId}")
public ResponseEntity<List<User>> getIntervenantsByPatients(@PathVariable int patientId) {
    try {
        List<User> intervenants = userService.getIntervenantsByStudent(patientId);
        return ResponseEntity.ok(intervenants);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(Collections.emptyList());
    }
}

     // Récupérer tous les utilisateurs ayant le rôle "PATIENT"
     @GetMapping("/intervenants/search")
     public ResponseEntity<List<User>> searchIntervenants(@RequestParam String searchTerm) {
         try {
             List<User> intervenants = userService.searchIntervenants(searchTerm);
             return ResponseEntity.ok(intervenants);
         } catch (Exception e) {
             e.printStackTrace();
             return ResponseEntity.status(500).body(Collections.emptyList());
         }
     }

    @PostMapping("/details")
    public ResponseEntity<?> getPatientDetails(@RequestBody Map<String, List<Integer>> data) {
        try {
            List<Integer> patientIds = data.get("patientIds");
            List<User> patients = userService.getPatientsByIds(patientIds);
            return ResponseEntity.ok(patients);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des détails des patients : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }

   


    /**
     * Récupère les enseignants associés à une liste de patients.
     * @param data Map contenant les IDs des patients.
     * @return Liste des enseignants associés.
     */
    @PostMapping("/teachers")
    public ResponseEntity<?> getTeachersForPatients(@RequestBody Map<String, List<Integer>> data) {
        try {
            List<Integer> patientIds = data.get("patientIds"); // Récupération des IDs des patients
            System.out.println("Requête reçue pour les patients : " + patientIds);

            // Appel au service pour récupérer les enseignants
            List<Teacher> teachers = teacherService.getTeachersForPatients(patientIds);
            return ResponseEntity.ok(teachers);

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des enseignants : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }
    

    
    /**
     * Récupère les enseignants associés à une liste de patients.
     * @param data Map contenant les IDs des patients.
     * @return Liste des enseignants associés.
     */
    @PostMapping("/orthophonistes")
    public ResponseEntity<?> getOrthophonistesForPatients(@RequestBody Map<String, List<Integer>> data) {
        try {
            List<Integer> patientIds = data.get("patientIds"); // Récupération des IDs des patients
            System.out.println("Requête reçue pour les patients : " + patientIds);

            // Appel au service pour récupérer les enseignants
            List<OrthoPatient> orthos = userService.getOrthosByPatients(patientIds);
            System.out.println("Carré je tenvoi ca"+orthos);
            return ResponseEntity.ok(orthos);

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des enseignants : " + e.getMessage());
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }
    
}


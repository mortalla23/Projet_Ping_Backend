package fr.esigelec.ping.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.esigelec.ping.model.User;
import fr.esigelec.ping.service.LinkService;
import fr.esigelec.ping.service.UserService;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/teacher")
@CrossOrigin(origins = "http://localhost:3000")
public class TeacherController {

    @Autowired
    private UserService userService;

    @Autowired
    private LinkService linkService;

    @GetMapping("/students")
    public ResponseEntity<?> getStudentsForTeacher(@RequestParam(required = false) String teacherId, @RequestParam(required = false) String email, @RequestParam(required = false) String searchTerm) {
        try {
            if (email != null) {
                System.out.println("Recherche par email pour : " + email);  // Log pour l'email
                User student = userService.findStudentByEmail(email);
                if (student == null) {
                    return ResponseEntity.status(404).body("Aucun élève trouvé avec cet email.");
                }
                return ResponseEntity.ok(student);
            }

            if (teacherId != null && ObjectId.isValid(teacherId)) {
                System.out.println("Recherche par teacherId pour : " + teacherId);  // Log pour teacherId
                List<User> students = userService.getStudentsByTeacher(teacherId);
                if (students.isEmpty()) {
                    return ResponseEntity.ok("Aucun élève trouvé pour cet enseignant.");
                }
                return ResponseEntity.ok(students);
            }
            
            if (searchTerm != null) {
                List<User> students = userService.searchStudents(searchTerm);
                return ResponseEntity.ok(students);
            }
            
            return ResponseEntity.badRequest().body("Paramètre manquant.");

        } catch (Exception e) {
            System.out.println("Erreur dans la méthode : " + e.getMessage());  // Log de l'erreur
            return ResponseEntity.status(500).body("Erreur interne du serveur : " + e.getMessage());
        }
    }


    @PostMapping("/link-student-by-email")
    public ResponseEntity<?> linkStudentToTeacherByEmail(@RequestBody Map<String, String> payload) {
        try {
            System.out.println("Méthode linkStudentToTeacherByEmail appelée");

            if (payload == null || payload.isEmpty()) {
                System.out.println("Payload est null ou vide");
                return ResponseEntity.badRequest().body("Payload est requis.");
            }
            System.out.println("Payload reçu : " + payload);

            String teacherId = payload.get("teacherId");
            String studentEmail = payload.get("studentEmail");

            System.out.println("teacherId : " + teacherId);
            System.out.println("studentEmail : " + studentEmail);

            if (teacherId == null || studentEmail == null) {
                System.out.println("Un des champs est null");
                return ResponseEntity.badRequest().body("Les champs 'teacherId' et 'studentEmail' sont requis.");
            }

            // Appeler le service pour rechercher l'étudiant et créer le lien
            User linkedStudent = userService.findAndLinkStudent(teacherId, studentEmail);

            System.out.println("Élève associé : " + linkedStudent);
            return ResponseEntity.ok(linkedStudent);
        } catch (RuntimeException e) {
            e.printStackTrace(); // Afficher les logs complets
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); // Afficher les logs complets
            return ResponseEntity.status(500).body("Erreur interne du serveur.");
        }
    }

}

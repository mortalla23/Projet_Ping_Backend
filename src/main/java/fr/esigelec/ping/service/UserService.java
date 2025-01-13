package fr.esigelec.ping.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.Role;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findStudentByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Aucun étudiant trouvé avec cet email"));
    }

    // méthode dans le service pour rechercher un étudiant par email et l'associer à un enseignant.
    public User findAndLinkStudent(String teacherId, String studentEmail) {
        // Trouver l'étudiant par email
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Aucun étudiant trouvé avec cet email : " + studentEmail));

        // Vérifiez si une association existe déjà
        boolean isAlreadyLinked = linkRepository.existsByLinkedToAndLinkerId(student.getId(), teacherId);
        if (isAlreadyLinked) {
            throw new RuntimeException("L'étudiant est déjà associé à cet enseignant.");
        }

        // Créez une nouvelle association
        Link link = new Link(teacherId, student.getId(), "active");
        linkRepository.save(link);

        return student;
    }

    
    public User authenticate(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Mot de passe incorrect");
        }
        return user;
    }

    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public List<User> getStudentsByTeacher(String teacherId) {
        // Vérifiez que teacherId est valide
        if (!ObjectId.isValid(teacherId)) {
            throw new IllegalArgumentException("teacherId n'est pas un ObjectId valide.");
        }

        System.out.println("Récupération des élèves pour l'enseignant avec teacherId : " + teacherId);

        // Récupérer tous les liens entre les enseignants et les élèves
        List<Link> links = linkRepository.findLinksByTeacherId(teacherId);

        // Vérifiez si des liens ont été trouvés pour l'enseignant
        if (links.isEmpty()) {
            System.out.println("Aucun lien trouvé pour cet enseignant.");
            return List.of();  // Retourner une liste vide si aucun élève n'est associé
        }

        // Extraire les IDs des élèves associés à l'enseignant et les convertir en ObjectId
        List<String> studentIds = links.stream()
                                       .map(Link::getLinkedTo)  // Utilisez le getter pour récupérer l'ID de l'élève
                                       .collect(Collectors.toList());

        System.out.println("IDs des élèves trouvés : " + studentIds);

        // Assurez-vous que les IDs sont correctement formatés en ObjectId
        List<ObjectId> objectIds = studentIds.stream()
                                             .map(ObjectId::new)
                                             .collect(Collectors.toList());

        // Rechercher les étudiants dans la base de données avec les IDs récupérés
        List<User> students = userRepository.findAllByIds(objectIds);

        System.out.println("Élèves récupérés : " + students);

        return students;
    }


    public List<User> searchStudents(String searchTerm) {
        return userRepository.findByUsernameContainingOrEmailContaining(searchTerm, searchTerm);
    }


    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Utilisateur introuvable avec l'ID : " + userId);
        }
        userRepository.deleteById(userId);
    }
}

package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import fr.esigelec.ping.model.enums.Role;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkService linkService;

    // 🔍 Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 🔍 Récupérer un utilisateur par son ID
    public User getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("L'utilisateur avec l'ID " + userId + " n'existe pas."));
    }

    // ✅ Vérifier si un utilisateur existe par email
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // ✅ Enregistre un utilisateur avec vérification du rôle
    public User registerUser(User user) {
        // 🔒 Hashage du mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Mettre l'id
        user.setId(generateUniqueUserId());

        // ✅ Vérifier le rôle
        if (!Role.isValidRole(user.getRole().name())) {
            throw new IllegalArgumentException("Rôle invalide : " + user.getRole());
        }

        return userRepository.save(user);
    }
    
     // ✅ Méthode de connexion
     public Optional<User> login(String email, String password) {
        // 🔍 Vérifier si l'utilisateur existe par email
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // 🔒 Vérifier si le mot de passe correspond au hash stocké
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);  // ✅ Connexion réussie
            }
        }
        return Optional.empty();  // ❌ Identifiants invalides
    }

    // ❌ Supprimer un utilisateur
    public void deleteUser(int userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID " + userId + " n'existe pas.");
        }
        userRepository.deleteById(userId);
    }

    //Student par email
    public User findStudentByEmail(String email) {
        // Récupérer l'utilisateur par email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur avec l'email " + email + " introuvable."));

        // Vérifier que le rôle est "STUDENT"
        if (user.getRole() != Role.PATIENT) {
            throw new IllegalArgumentException("L'utilisateur avec l'email " + email + " n'est pas un étudiant.");
        }

        return user; // ✅ Retourne l'utilisateur trouvé
    }

    //Chercher des étudiants
    public List<User> searchStudents(String searchTerm) {
        return userRepository.findByUsernameContainingOrEmailContaining(searchTerm, searchTerm);
    }

     

    public List<User> getStudentsByTeacher(int teacherId) {
        // Vérifier que teacherId est valide
        if (teacherId <= 0) {
            throw new IllegalArgumentException("teacherId doit être un entier positif.");
        }
    
        System.out.println("Récupération des élèves pour l'enseignant avec teacherId : " + teacherId);
    
        // Récupérer tous les liens entre les enseignants et les élèves
        List<Link> links = linkRepository.findLinksByTeacherId(teacherId);
    
        // Vérifiez si des liens ont été trouvés pour l'enseignant
        if (links.isEmpty()) {
            System.out.println("Aucun lien trouvé pour cet enseignant.");
            return List.of(); // Retourner une liste vide si aucun élève n'est associé
        }
    
        // Extraire les IDs des élèves associés à l'enseignant
        List<Integer> studentIds = links.stream()
                                        .map(Link::getLinkedTo) // Utiliser le getter pour récupérer l'ID de l'élève
                                        .collect(Collectors.toList());
    
        System.out.println("IDs des élèves trouvés : " + studentIds);
    
        // Rechercher les étudiants dans la base de données avec les IDs récupérés
        List<User> students = userRepository.findAllByIds(studentIds);
    
        System.out.println("Élèves récupérés : " + students);
    
        return students;
    }

     // méthode dans le service pour rechercher un étudiant par email et l'associer à un enseignant.
    public User findAndLinkStudent(int teacherId, String studentEmail) {
        // Trouver l'étudiant par email
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Aucun étudiant trouvé avec cet email : " + studentEmail));

        // Vérifiez si une association existe déjà
        boolean isAlreadyLinked = linkRepository.existsByLinkedToAndLinkerId(student.getId(), teacherId);
        if (isAlreadyLinked) {
            throw new RuntimeException("L'étudiant est déjà associé à cet enseignant.");
        }

        // Créez une nouvelle association
        linkService.createLink(teacherId, student.getId());
        

        return student;
    }

    


    
    // 🔍 Recherche progressive des utilisateurs par préfixe de username
    public List<User> searchUsersByUsername(String prefix) {
        return userRepository.findByUsernameStartingWith(prefix);
    }

    



    // 🔄 Génération d'un ID unique pour le message
    private int generateUniqueUserId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (userRepository.existsById(id));
        return id;
    }
    
}

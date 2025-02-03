package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.OrthoPatient;
import fr.esigelec.ping.model.Teacher;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.model.enums.Role;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkService linkService;

     // Méthode utilitaire pour supprimer les mots de passe d'une liste d'utilisateurs
     private List<User> removePasswords(List<User> users) {
        return users.stream()
                .map(this::removePassword)
                .collect(Collectors.toList());
    }

    // Méthode utilitaire pour supprimer le mot de passe d'un utilisateur
    private User removePassword(User user) {
        user.setPassword(null);
        return user;
    }
    // 🔍 Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return removePasswords(userRepository.findAll());
    }

    // 🔍 Récupérer un utilisateur par son ID
    public User getUserById(int userId) {
       return userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("Utilisateur avec l'id " + userId + " introuvable."));
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
    

     // Method to find user by email

     public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur avec l'email " + email + " introuvable."));
    }

     // ✅ Méthode de connexion
     public Optional<User> login(String email, String password) {
        // 🔍 Vérifier si l'utilisateur existe par email
        System.out.println("Requête de service :bienvenue ");
        Optional<User> userOpt = Optional.empty();
         
        try {
            System.out.println("de service :bienvenue ");
            userOpt = userRepository.findByEmail(email);
            logger.info("User search result: {}", userOpt.isPresent() ? "User found" : "User not found");
        } catch (Exception e) {
            logger.error("Error finding user by email: {}", email, e);
        }
        System.out.println("Utilisateur en recherche : ");
            
        if (userOpt.isPresent()) {
            System.out.println("Utilisateur trouvé : ");
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

        return removePassword(user); // ✅ Retourne l'utilisateur trouvé
    }

 // Rechercher des patients par nom d'utilisateur ou email
    public List<User> searchPatients(String searchTerm) {
        List<User> allUsers = userRepository.findByUsernameContainingOrEmailContaining(searchTerm, searchTerm);
        allUsers=removePasswords(allUsers);
        System.out.println("les patients trouvés"+allUsers);
        return allUsers.stream()
                .filter(user -> user.getRole() == Role.PATIENT)
                .toList();
    }

    // Rechercher des patients par nom d'utilisateur ou email
    public List<User> searchIntervenants(String searchTerm) {
        List<User> allUsers = userRepository.findByUsernameContainingOrEmailContaining(searchTerm, searchTerm);
        allUsers=removePasswords(allUsers);
        return allUsers.stream()
                .filter(user -> user.getRole() == Role.ENSEIGNANT | user.getRole()== Role.ORTHOPHONIST)
                .toList();
    }

    public List<User> getAllPatientsSorted() {
        return removePasswords(userRepository.findAll().stream()
                .filter(user -> user.getRole() == Role.PATIENT)
                .sorted(Comparator.comparing(User::getUsername))
                .collect(Collectors.toList()));
    }


     
/**
     * Récupère les enseignants liés à une liste de patients.
     *
     * @param patientIds Liste des IDs des patients
     * @return Liste des enseignants liés à ces patients
     */
    public List<OrthoPatient> getOrthosByPatients(List<Integer> patientIds) {
        // Étape 1 : Récupérer les liens pour les patients avec le rôle "ORTHOPHONISTE"
List<Link> links = Stream.concat(
    linkRepository.findByLinkedToIn(patientIds).stream()
            .filter(link -> "ORTHOPHONISTE".equals(link.getRole())), // Filtrer uniquement les enseignants
    linkRepository.findByLinkerIdIn(patientIds).stream()
            .filter(link -> "ORTHOPHONISTE".equals(link.getRole()) // Filtrer uniquement les enseignants
    )
).collect(Collectors.toList());
        System.out.println("Liens orthos trouvés pour les patients : " + links);

       // Étape 2 : Extraire les IDs uniques des enseignants
List<Integer> orthoIds = links.stream()
        .flatMap(link -> patientIds.stream()
                .flatMap(patientId -> {
                    List<Integer> ids = new ArrayList<>();
                    if (!(link.getLinkerId()==patientId)) {
                        ids.add(link.getLinkerId());
                    }
                    if (!(link.getLinkedTo()==patientId)) {
                        ids.add(link.getLinkedTo());
                    }
                    return ids.stream();
                })
        )
        .distinct() // Assurez-vous d'avoir des IDs uniques
        .collect(Collectors.toList());

System.out.println("Orthos trouvés (IDs) : " + orthoIds);
      // Étape 3 : Récupérer les utilisateurs ortho associés aux IDs avec l'ID patient
List<OrthoPatient> orthoPatients = new ArrayList<>();

for (Integer patientId : patientIds) {
    for (Integer orthoId : orthoIds) {
        User ortho = userRepository.findById(orthoId).orElse(null);
        if (ortho != null) {
            System.out.println("Ortho associé : " + ortho);
            orthoPatients.add(new OrthoPatient(ortho, patientId));
        }
    }
}

System.out.println("Orthos associés aux patients : " + orthoPatients);
return orthoPatients;
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

        return removePasswords(students);
    }
        public List<User> getIntervenantsByStudent(int studentId) {
            // Vérifier que teacherId est valide
            if (studentId <= 0) {
                throw new IllegalArgumentException("studentId doit être un entier positif.");
            }
        
            System.out.println("Récupération des intervenants pour l'étudiant avec sudentId : " + studentId);
        
            // Récupérer tous les liens entre les intervenants et les élèves
            List<Link> links = linkRepository.findLinksByStudentsId(studentId);
        
            // Vérifiez si des liens ont été trouvés pour l'enseignant
            if (links.isEmpty()) {
                System.out.println("Aucun lien trouvé pour cet étudiant.");
                return List.of(); // Retourner une liste vide si aucun élève n'est associé
            }
    
        // Extraire les IDs des élèves associés à l'enseignant
        List<Integer> intervenantIds = links.stream()
                                        .map(Link::getLinkedTo) // Utiliser le getter pour récupérer l'ID de l'élève
                                        .collect(Collectors.toList());
       intervenantIds.removeIf(intervenant -> intervenant.equals(studentId));
        System.out.println("IDs des intervenants trouvés : " + intervenantIds);
    
        // Rechercher les étudiants dans la base de données avec les IDs récupérés
        List<User> intervenants = userRepository.findAllByIds(intervenantIds);
    
        System.out.println("Élèves récupérés : " + intervenants);
    
        return removePasswords(intervenants);
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
        Link newLink = linkService.createLink(teacherId, student.getId(), LinkValidation.ONGOING, "TEACHER");


        System.out.println("Lien créé avec succès : " + newLink);

        return student;	
    }


    


    
    // 🔍 Recherche progressive des utilisateurs par préfixe de username
    public List<User> searchUsersByUsername(String prefix) {
        return removePasswords(userRepository.findByUsernameStartingWith(prefix));
    }

    



    // 🔄 Génération d'un ID unique pour le message
    private int generateUniqueUserId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (userRepository.existsById(id));
        return id;
    }

    public List<User> getUsersByRole(Role role) {
        return removePasswords(userRepository.findAll().stream()
                .filter(user -> user.getRole() == role)
                .collect(Collectors.toList()));
    }
    
    //Chercher des étudiants
    public List<User> searchStudents(String searchTerm) {
        return userRepository.findByUsernameContainingOrEmailContaining(searchTerm, searchTerm);
    }

    public List<User> getUsersByIds(List<Integer> ids) {
        return removePasswords(userRepository.findAllById(ids));
    }
    public List<User> getPatientsByIds(List<Integer> ids) {
        return removePasswords(userRepository.findAllById(ids));
    }

    
}

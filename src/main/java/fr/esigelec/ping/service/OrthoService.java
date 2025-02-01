package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.Teacher;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrthoService {

    @Autowired
    private LinkRepository linkRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Récupérer tous les patients associés à un orthophoniste via Link
    public List<User> getAllStudents(int orthoId) {
        // Récupérer les liens où l'orthophoniste est le linkerId
        List<Link> links = linkRepository.findLinksByTeacherId(orthoId);
        
        // Utiliser les ids des patients pour récupérer les informations sur les patients
        List<Integer> studentIds = links.stream()
                                       .map(Link::getLinkedTo)  // Extrait les IDs des patients
                                       .collect(Collectors.toList());
                                       
        return userRepository.findAllById(studentIds); // Récupérer les patients par leurs IDs
    }

    // Ajouter un patient à l'orthophoniste via un lien
    public Link addStudentToOrtho(int orthoId, int studentId) {
        // Vérifie si le lien existe déjà
        if (linkRepository.existsByLinkerIdAndLinkedTo(orthoId, studentId)) {
            throw new IllegalArgumentException("Cet élève est déjà associé à cet orthophoniste.");
        }

        // Créer un lien entre l'orthophoniste et le patient
        Link link = new Link();
        link.setLinkerId(orthoId);
        link.setLinkedTo(studentId);
        link.setValidate(LinkValidation.ONGOING); // Initialiser à "false"

        // Sauvegarder le lien dans la base de données
        return linkRepository.save(link);
    }

    // Vérifier si un lien existe entre un orthophoniste et un patient
    public boolean linkExists(int orthoId, int studentId) {
        return linkRepository.existsByLinkerIdAndLinkedTo(orthoId, studentId);
           
    }
  /*  public List<User> getValidatedPatients(int orthoId) {
        return linkRepository.findValidatedPatientsByOrthoId(orthoId);
    }*/
    public List<User> getValidatedPatients(int orthoId) {
        List<Link> validatedLinks = linkRepository.findValidatedLinksByLinkerId(orthoId);
        List<Integer> patientIds = validatedLinks.stream()
                                                 .map(Link::getLinkedTo)
                                                 .collect(Collectors.toList());
        return userRepository.findAllById(patientIds); // Récupère les patients depuis leur ID
    }
    

    /**
     * Récupère les enseignants liés à une liste de patients.
     *
     * @param patientIds Liste des IDs des patients
     * @return Liste des enseignants liés à ces patients
     */
    public List<User> getOrthoForPatients(List<Integer> patientIds) {
        // Étape 1 : Récupérer les liens pour les patients avec le rôle "ENSEIGNANT"
        List<Link> links = linkRepository.findByLinkedToIn(patientIds)
                .stream()
                .filter(link -> "ORTHOPHONISTE".equals(link.getRole())) // Filtrer uniquement les enseignants
                .collect(Collectors.toList());
        System.out.println("Liens Ortho trouvés pour les patients : " + links);

        // Étape 2 : Extraire les IDs uniques des enseignants
        List<Integer> orthoIds = links.stream()
                .map(Link::getLinkerId)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Ortho trouvés (IDs) : " + orthoIds);

        // Étape 3 : Récupérer les informations des enseignants à partir de leurs IDs
        List<User> orthos = links.stream()
                .map(link -> {
                    User ortho = userRepository.findById(link.getLinkerId()).orElse(null);
                    return ortho;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        System.out.println("Enseignants associés aux patients : " + orthos);
        return orthos;
    }
}

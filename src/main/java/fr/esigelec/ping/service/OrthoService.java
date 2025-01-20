package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}

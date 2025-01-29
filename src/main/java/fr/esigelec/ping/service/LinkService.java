package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.Teacher;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Récupère tous les liens pour un enseignant donné.
     */
    public List<Link> getStudentsByTeacherId(int teacherId) {
        return linkRepository.findLinksByTeacherId(teacherId);
    }

     // Méthode pour vérifier si deux utilisateurs sont liés
     public boolean isLinkedWith(int userId1, int userId2, String role, LinkValidation validationStatus) {
        // Vérifier si un lien existe entre userId1 et userId2, avec le rôle et la validation donnés
        Optional<Link> link = linkRepository.findLinkByLinkerIdAndLinkedToAndRoleAndValidate(userId1, userId2, role, validationStatus);
        return link.isPresent();
    }
    /**
     * Crée un lien entre un enseignant et un étudiant.
     */
    public Link createLink(int linkerId, int linkedTo, LinkValidation status, String role) {
        if (linkerId <= 0 || linkedTo <= 0) {
            throw new IllegalArgumentException("Les IDs doivent être des entiers positifs.");
        }

        Link newLink = new Link();
        newLink.setLinkerId(linkerId);
        newLink.setLinkedTo(linkedTo);
        newLink.setValidate(status);
        newLink.setRole(role); // Définir le rôle du lien

        return linkRepository.save(newLink);
    }





    private String generateUniqueMongoId() {
        return java.util.UUID.randomUUID().toString(); // Génère un identifiant unique
    }
    


    /**
     * Met à jour le statut de validation d'un lien.
     */
    public void updateValidationStatus(int linkId, LinkValidation status) {
        // Vérifie si le lien existe
        Link link = linkRepository.findById(linkId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Le lien avec l'ID spécifié n'existe pas."));

        // Met à jour le statut
        link.setValidate(status);
        linkRepository.save(link);

        System.out.println("Statut du lien mis à jour : " + status);
    }

    /**
     * Récupère tous les liens validés pour un orthophoniste donné.
     */
    public List<Link> getValidatedLinksByOrthophonist(int orthoId) {
        return linkRepository.findLinksByTeacherIdAndValidate(orthoId, LinkValidation.VALIDATED);
    }

    
    //public List<Link> getValidatedLinks(int linkerId, String role) {
    //    return linkRepository.findValidatedLinksByLinkerIdAndRole(linkerId, role);
 //   }


    /**
     * Récupère tous les liens associés à un patient donné.
     */
    public List<Link> getLinksByPatientId(int patientId) {
        return linkRepository.findByLinkedTo(patientId);
    }

    /**
     * Supprime un lien par son ID.
     */
    public void deleteLink(String linkId) { // Utilise String pour `_id`
        System.out.println("Tentative de suppression du lien avec ID : " + linkId);

        // Vérifie si le lien existe
        if (!linkRepository.existsById(linkId)) {
            System.out.println("Lien introuvable : ID " + linkId);
            throw new IllegalArgumentException("Le lien avec l'ID spécifié n'existe pas.");
        }

        // Supprime le lien
        linkRepository.deleteById(linkId);
        System.out.println("Lien supprimé avec succès : ID " + linkId);
    }


    /**
     * Génère un ID unique pour un lien.
     */
    private int generateUniqueLinkId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (linkRepository.existsById(id));
        return id;
    }
    
    public Link saveLink(Link link) {
        return linkRepository.save(link);
    }
    public List<Teacher> getTeachersByPatientIds(List<Integer> patientIds) {
        List<Link> links = linkRepository.findAllByLinkedToIn(patientIds);
        List<Teacher> teachers = new ArrayList<>();

        for (Link link : links) {
            if (link.getRole().equals("TEACHER")) {
                User teacher = userRepository.findById(link.getLinkerId())
                        .orElse(null);
                if (teacher != null) {
                    teachers.add(new Teacher(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), link.getLinkedTo()));
                }
            }
        }
        return teachers;
    }

    public List<Link> getValidatedLinks(int linkerId) {
        // Vérifiez si linkerId est valide
        if (linkerId <= 0) {
            throw new IllegalArgumentException("L'ID du linker doit être positif.");
        }

        // Récupérez les liens validés pour le linkerId
        List<Link> links = linkRepository.findValidatedLinksByLinkerId(linkerId);
        if (links == null || links.isEmpty()) {
            throw new IllegalArgumentException("Aucun lien validé trouvé pour le linker ID " + linkerId);
        }

        return links;
    }

    public List<Link> getValidatedLinks(int linkerId, String role) {
        // Vérifiez si linkerId et rôle sont valides
        if (linkerId <= 0) {
            throw new IllegalArgumentException("L'ID du linker doit être positif.");
        }
        if (role == null || role.isEmpty()) {
            throw new IllegalArgumentException("Le rôle ne peut pas être vide.");
        }

        // Récupérez les liens validés pour le linkerId et le rôle
        List<Link> links = linkRepository.findValidatedLinksByLinkerIdAndRole(linkerId, role);
        if (links == null || links.isEmpty()) {
            throw new IllegalArgumentException(
                "Aucun lien validé trouvé pour le linker ID " + linkerId + " avec le rôle " + role
            );
        }

        return links;
    }


    
}

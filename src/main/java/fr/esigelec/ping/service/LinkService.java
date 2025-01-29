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

    /**
     * Récupère tous les liens associés à un patient donné.
     */
    public List<Link> getLinksByPatientId(int patientId) {
        // Récupère tous les liens liés au patient
        List<Link> links = linkRepository.findByLinkedTo(patientId);

        // Ajoute l'email de l'orthophoniste si le rôle est ORTHOPHONISTE
        for (Link link : links) {
            if ("ORTHOPHONISTE".equals(link.getRole())) {
                // Récupère l'utilisateur (orthophoniste) associé au linkerId
                User ortho = userRepository.findById(link.getLinkerId()).orElse(null);
                if (ortho != null) {
                    // Ajoute l'email de l'orthophoniste au lien
                    link.setOrthoEmail(ortho.getEmail());
                }
            }
        }
        return links;
    }



    private String generateUniqueMongoId() {
        return java.util.UUID.randomUUID().toString(); // Génère un identifiant unique
    }
    


    /**
     * Met à jour le statut de validation d'un lien.
     */
    public void updateValidationStatus(String linkId, LinkValidation  status) {
      //  status = status.trim();  // Enlever les espaces inutiles autour de la chaîne

        try {
        	System.out.println("Statut reçu : '" + status + "'");
            // Vérification de la validité du statut (automatiquement géré par valueOf)
           // LinkValidation linkValidationStatus = LinkValidation.valueOf(status); // Exception si invalide
        	 LinkValidation linkValidationStatus = status;
            Link link = linkRepository.findById(linkId)
                    .orElseThrow(() -> new IllegalArgumentException("Le lien avec l'ID spécifié n'existe pas."));

            link.setValidate(linkValidationStatus);  // Mise à jour du statut avec la valeur valide
            linkRepository.save(link);

            System.out.println("Statut du lien mis à jour : " + linkValidationStatus);

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur de validation du statut : " + e.getMessage());
            throw new RuntimeException("Le statut du lien est invalide : " + status, e);
        }
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

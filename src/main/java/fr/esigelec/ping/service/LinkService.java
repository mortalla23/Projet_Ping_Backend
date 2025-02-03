package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.Teacher;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.LinkValidation;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
  

    /**
     * Récupère tous les liens associés à un patient donné.
     */
  
    public List<Link> getLinksByPatientId(int patientId) {
        // Récupère tous les liens liés au patient
        List<Link> links = linkRepository.findByLinkedTo(patientId);

        // Ajoute l'email de l'orthophoniste ou de l'enseignant selon le rôle
        for (Link link : links) {
            if ("ORTHOPHONISTE".equals(link.getRole())) {
                // Récupère l'utilisateur (orthophoniste) associé au linkerId
                User ortho = userRepository.findById(link.getLinkerId()).orElse(null);
                if (ortho != null) {
                    // Ajoute l'email de l'orthophoniste au lien
                    link.setOrthoEmail(ortho.getEmail());
                }
            } else if ("TEACHER".equals(link.getRole())) {
                // Récupère l'utilisateur (enseignant) associé au linkerId
                User teacher = userRepository.findById(link.getLinkerId()).orElse(null);
                if (teacher != null) {
                    // Ajoute l'email de l'enseignant au lien
                    link.setTeacherEmail(teacher.getEmail());
                }
            }
        }
        return links;
    }


    public List<Link> getValidatedLinksByTeacherId(int teacherId) {
        // Filtrer les liens validés pour l'enseignant actuel
        return linkRepository.findByLinkerIdAndValidate(teacherId, "VALIDATED");
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
        List<Link> links = linkRepository.findLinksByteacherId(linkerId);
        if (links == null || links.isEmpty()) {
            throw new IllegalArgumentException("Aucun lien validé trouvé pour le linker ID " + linkerId);
        }

        return links;
    }

    


    public List<Link> getOngoingLinks() {
        List<Link> allLinks = linkRepository.findAll();
        System.out.print("🔍 Liens récupérés depuis la BDD : {}"+allLinks); // 📌 Debug

        List<Link> ongoingLinks = allLinks.stream()
                .filter(link -> link.getValidate() == LinkValidation.ONGOING)
                .collect(Collectors.toList());

        System.out.print("📌 Liens avec statut ONGOING : {}"+ ongoingLinks); // 📌 Debug
        return ongoingLinks;
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

    
    @Transactional
    public boolean validateLink(String linkId) {
        System.out.println("🔍 Recherche du lien avec ID MongoDB: " + linkId);

        Optional<Link> optionalLink = linkRepository.findById(linkId);

        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            link.setValidate(LinkValidation.VALIDATED);
            linkRepository.save(link);
            System.out.println("✅ Lien validé avec succès !");
            return true;
        } else {
            System.out.println("🚨 Aucun lien trouvé !");
            return false;
        }
    }

    @Transactional
    public boolean rejectLink(String linkId) {
        System.out.println("🔍 Recherche du lien avec ID MongoDB: " + linkId);

        Optional<Link> optionalLink = linkRepository.findById(linkId);

        if (optionalLink.isPresent()) {
            Link link = optionalLink.get();
            link.setValidate(LinkValidation.REFUSED);
            linkRepository.save(link);
            System.out.println("✅ Lien refusé avec succès !");
            return true;
        } else {
            System.out.println("🚨 Aucun lien trouvé !");
            return false;
        }
    }

    
}

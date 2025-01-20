package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.model.enums.Role;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.esigelec.ping.model.enums.LinkValidation;

import java.util.List;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Link> getStudentsByTeacherId(int teacherId) {
        return linkRepository.findLinksByTeacherId(teacherId);
    }

    public Link createLink(int teacherId, int studentId) {
        // Vérifiez que l'enseignant a le rôle TEACHER
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("L'enseignant avec l'ID spécifié est introuvable."));
        if (!Role.TEACHER.equals(teacher.getRole())) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID spécifié n'est pas un enseignant.");
        }

        // Vérifiez que l'étudiant a le rôle PATIENT
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("L'étudiant avec l'ID spécifié est introuvable."));
        if (!Role.PATIENT.equals(student.getRole())) {
            throw new IllegalArgumentException("L'utilisateur avec l'ID spécifié n'est pas un étudiant.");
        }

        // Vérifiez l'existence du lien avant de le créer
        if (linkRepository.existsByLinkerIdAndLinkedTo(teacherId, studentId)) {
            throw new IllegalArgumentException("Un lien existe déjà entre cet enseignant et cet étudiant.");
        }

        // Créez le lien
        Link link = new Link();
        link.setId(generateUniqueLinkId());
        link.setLinkerId(teacherId);
        link.setLinkedTo(studentId);
        link.setValidate(LinkValidation.ONGOING);
        linkRepository.save(link);

        System.out.println("Lien créé avec succès entre teacherId: " + teacherId + " et studentId: " + studentId);

        return link;
    }

      // 🔄 Génération d'un ID unique pour le message
      private int generateUniqueLinkId() {
        int id;
        do {
            id = (int) (Math.random() * 100000);
        } while (linkRepository.existsById(id));
        return id;
    }
}

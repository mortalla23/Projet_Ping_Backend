package fr.esigelec.ping.service;

import fr.esigelec.ping.model.Link;
import fr.esigelec.ping.model.Teacher;
import fr.esigelec.ping.model.User;
import fr.esigelec.ping.repository.LinkRepository;
import fr.esigelec.ping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service // Annotation pour indiquer que cette classe est un service Spring
public class TeacherService {

    @Autowired
    private LinkRepository linkRepository; // Injecte le repository pour les relations entre enseignants et patients

    
    @Autowired
    private UserRepository userRepository; // Injecte le repository pour récupérer les informations des utilisateurs

    /**
     * Récupère les enseignants liés à une liste de patients.
     *
     * @param patientIds Liste des IDs des patients
     * @return Liste des enseignants liés à ces patients
     */
    public List<Teacher> getTeachersForPatients(List<Integer> patientIds) {
        // Étape 1 : Récupérer les liens pour les patients avec le rôle "ENSEIGNANT"
       List<Link> links = Stream.concat(
    linkRepository.findByLinkedToIn(patientIds).stream()
            .filter(link -> "ORTHOPHONISTE".equals(link.getRole())), // Filtrer uniquement les enseignants
    linkRepository.findByLinkerIdIn(patientIds).stream()
            .filter(link -> "ORTHOPHONISTE".equals(link.getRole()) // Filtrer uniquement les enseignants
    )
).collect(Collectors.toList());
        System.out.println("Liens enseignants trouvés pour les patients : " + links);

        // Étape 2 : Extraire les IDs uniques des enseignants
        List<Integer> teacherIds = links.stream()
                .map(Link::getLinkerId)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Enseignants trouvés (IDs) : " + teacherIds);

        // Étape 3 : Récupérer les informations des enseignants à partir de leurs IDs
        List<Teacher> teachers = links.stream()
                .map(link -> {
                    User teacher = userRepository.findById(link.getLinkerId()).orElse(null);
                    if (teacher != null) {
                        Teacher t = new Teacher(teacher.getId(), teacher.getFirstName(), teacher.getLastName(), link.getLinkedTo());
                        System.out.println("Enseignant associé : " + t);
                        return t;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        System.out.println("Enseignants associés aux patients : " + teachers);
        return teachers;
    }



}

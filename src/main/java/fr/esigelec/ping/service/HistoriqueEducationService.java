package fr.esigelec.ping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.esigelec.ping.model.HistoriqueEducation;
import fr.esigelec.ping.repository.HistoriqueEducationRepository;

@Service
public class HistoriqueEducationService {

    @Autowired
    private HistoriqueEducationRepository historiqueEducationRepository;

    public HistoriqueEducation getHistoriqueEducationById(int id) {
        return historiqueEducationRepository.findById(id);
    }

    public boolean existsHistoriqueEducationById(int id) {
        return historiqueEducationRepository.existsById(id);
    }

    public void deleteHistoriqueEducationById(int id) {
        historiqueEducationRepository.deleteById(id);
    }

    public HistoriqueEducation createOrUpdateHistoriqueEducation(HistoriqueEducation historiqueEducation) {
        if (historiqueEducation.getId() == 0) { // Si c'est une création
            historiqueEducation.setId(generateUniqueUserId());
        }
        return historiqueEducationRepository.save(historiqueEducation);
    }

    // 🔄 Génération d'un ID unique pour le message
private int generateUniqueUserId() {
    int id;
    do {
        id = (int) (Math.random() * 100000);
    } while (historiqueEducationRepository.existsById(id));
    return id;
}
}

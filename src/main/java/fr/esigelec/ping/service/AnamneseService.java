package fr.esigelec.ping.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.esigelec.ping.model.Anamnese;
import fr.esigelec.ping.repository.AnamneseRepository;

@Service
public class AnamneseService {

    @Autowired
    private AnamneseRepository anamneseRepository;

    public Anamnese getAnamneseById(int id) {
        return anamneseRepository.findById(id);
    }

    public boolean existsAnamneseById(int id) {
        return anamneseRepository.existsById(id);
    }

    public void deleteAnamneseById(int id) {
        anamneseRepository.deleteById(id);
    }

    public Anamnese createOrUpdateAnamnese(Anamnese anamnese) {
        if (anamnese.getId() == 0) { // Si c'est une création
            anamnese.setId(generateUniqueUserId());
        }
        return anamneseRepository.save(anamnese);
    }
// 🔄 Génération d'un ID unique pour le message
private int generateUniqueUserId() {
    int id;
    do {
        id = (int) (Math.random() * 100000);
    } while (anamneseRepository.existsById(id));
    return id;
}

}

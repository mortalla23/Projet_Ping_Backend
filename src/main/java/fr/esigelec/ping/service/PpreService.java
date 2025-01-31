package fr.esigelec.ping.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.esigelec.ping.model.Ppre;
import fr.esigelec.ping.repository.PpreRepository;

@Service
public class PpreService {

    @Autowired
    private PpreRepository ppreRepository;

    public Ppre getPpreById(int id) {
        return ppreRepository.findById(id);
    }

    public boolean existsPpreById(int id) {
        return ppreRepository.existsById(id);
    }

    public void deletePpreById(int id) {
        ppreRepository.deleteById(id);
    }

    public Ppre createOrUpdatePpre(Ppre ppre) {
        if (ppre.getId() == 0) { // Si c'est une création
            ppre.setId(generateUniqueUserId());
        }
        return ppreRepository.save(ppre);
    }
// 🔄 Génération d'un ID unique pour le message
private int generateUniqueUserId() {
    int id;
    do {
        id = (int) (Math.random() * 100000);
    } while (ppreRepository.existsById(id));
    return id;
}

}

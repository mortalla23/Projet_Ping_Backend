package fr.esigelec.ping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.esigelec.ping.model.HistoriqueEducation;
import fr.esigelec.ping.model.HistoriqueSante;
import fr.esigelec.ping.repository.HistoriqueSanteRepository;

@Service
public class HistoriqueSanteService {

    @Autowired
    private HistoriqueSanteRepository historiqueSanteRepository;

    public HistoriqueSante getHistoriqueSanteById(int id) {
        return historiqueSanteRepository.findById(id);
    }

    public boolean existsHistoriqueSanteById(int id) {
        return historiqueSanteRepository.existsById(id);
    }

    public void deleteHistoriqueSanteById(int id) {
        historiqueSanteRepository.deleteById(id);
    }

    public HistoriqueSante createOrUpdateHistoriqueSante(HistoriqueSante historiqueSante) {
        if (historiqueSante.getId() == 0) { // Si c'est une création
            historiqueSante.setId(generateUniqueUserId());
        }
        return historiqueSanteRepository.save(historiqueSante);
    }

    // 🔄 Génération d'un ID unique pour le message
private int generateUniqueUserId() {
    int id;
    do {
        id = (int) (Math.random() * 100000);
    } while (historiqueSanteRepository.existsById(id));
    return id;
}
}

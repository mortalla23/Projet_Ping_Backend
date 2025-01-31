package fr.esigelec.ping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.esigelec.ping.model.Anamnese;
import fr.esigelec.ping.service.AnamneseService;

@RestController
@RequestMapping("/api/anamnese")
public class AnamneseController {

    @Autowired
    private AnamneseService anamneseService;

    @GetMapping("/{id}")
    public ResponseEntity<Anamnese> getAnamneseById(@PathVariable int id) {
        Anamnese anamnese = anamneseService.getAnamneseById(id);
        if (anamnese != null) {
            return ResponseEntity.ok(anamnese);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Anamnese> createOrUpdateAnamnese(@RequestBody Anamnese anamnese) {
        System.out.println("Id anamn" + anamnese.getId());
        anamneseService.deleteAnamneseById(anamnese.getId());
        Anamnese updatedAnamnese = anamneseService.createOrUpdateAnamnese(anamnese);
        return ResponseEntity.ok(updatedAnamnese);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnamneseById(@PathVariable int id) {
        if (anamneseService.existsAnamneseById(id)) {
            anamneseService.deleteAnamneseById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

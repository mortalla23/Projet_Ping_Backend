package fr.esigelec.ping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.esigelec.ping.model.Anamnese;
import fr.esigelec.ping.model.Ppre;
import fr.esigelec.ping.service.PpreService;

@RestController
@RequestMapping("/api/ppre")
public class PpreController {

    @Autowired
    private PpreService ppreService;

    @GetMapping("/{id}")
    public ResponseEntity<Ppre> getPpreById(@PathVariable int id) {
        Ppre ppre = ppreService.getPpreById(id);
        if (ppre != null) {
            return ResponseEntity.ok(ppre);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Ppre> createOrUpdatePpre(@RequestBody Ppre ppre) {
        System.out.println("Id ppre" + ppre.getId());
        ppreService.deletePpreById(ppre.getId());
        Ppre updatedPpre = ppreService.createOrUpdatePpre(ppre);
        return ResponseEntity.ok(updatedPpre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnamneseById(@PathVariable int id) {
        if (ppreService.existsPpreById(id)) {
            ppreService.deletePpreById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

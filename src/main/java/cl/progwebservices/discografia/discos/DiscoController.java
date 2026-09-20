package cl.progwebservices.discografia.discos;

import cl.progwebservices.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(value="/disco", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (!artistaRepo.existsById(disco.getIdArtista())) {
            return ResponseEntity.status(404).body("Artista no encontrado");
        }
        Disco guardado = discoRepo.save(disco);
        return ResponseEntity.status(201).body(guardado);
    }

    @GetMapping(value="/discos", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return ResponseEntity.ok(discoRepo.findAll());
    }

    @GetMapping(value="/disco/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable String id) {
        return discoRepo.findById(id)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value="/artista/{id}/discos", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable String id) {
        if (!artistaRepo.existsById(id)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(discoRepo.findDiscosByIdArtista(id));
    }
}

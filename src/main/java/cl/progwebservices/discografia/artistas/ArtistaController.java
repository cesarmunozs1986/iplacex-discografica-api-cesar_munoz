package cl.progwebservices.discografia.artistas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository repo;

    @PostMapping(value="/artista", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista guardado = repo.save(artista);
        return ResponseEntity.status(201).body(guardado);
    }

    @GetMapping(value="/artistas", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping(value="/artista/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable String id) {
        return repo.findById(id)
                   .map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value="/artista/{id}", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(@PathVariable String id, @RequestBody Artista artista) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artista.set_id(id);
        Artista actualizado = repo.save(artista);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping(value="/artista/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> HandleDeleteArtistaRequest(@PathVariable String id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.ok("Artista eliminado");
    }
}

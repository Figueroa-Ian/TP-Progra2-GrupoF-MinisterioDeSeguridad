
package com.PROGRA2.BACKEND.controller;


import com.PROGRA2.BACKEND.models.Delito;
import com.PROGRA2.BACKEND.repository.DelitoRepository;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/delitos")
@CrossOrigin(origins = "*")
public class DelitoController {

    private final DelitoRepository delitoRepository;

    public DelitoController(DelitoRepository delitoRepository) {
        this.delitoRepository = delitoRepository;
    }

    @GetMapping
    public List<Delito> listarTodos() {
        return delitoRepository.findAll().stream()
                .filter(d -> d.getIdTipoJuridico() != null
                        && d.getIdTipoAccion() != null
                        && d.getIdTipoIntencion() != null)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delito> buscarPorId(@PathVariable Long id) {
        return delitoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Delito> crearDelito(@RequestBody Delito delito) {
        if (delito.getDescripcion() == null || delito.getDescripcion().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La descripción del delito es obligatoria.");
        }
        if (delito.getIdTipoJuridico() == null || delito.getIdTipoAccion() == null
                || delito.getIdTipoIntencion() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Elegí los tres tipos del delito.");
        }
        delito.setDescripcion(delito.getDescripcion().trim());
        // La columna tipo es de la versión anterior y sigue siendo obligatoria en algunas BDD.
        if (delito.getTipo() == null || delito.getTipo().isBlank()) {
            delito.setTipo(delito.getDescripcion());
        }
        Delito nuevo = delitoRepository.save(delito);
        return ResponseEntity.ok(nuevo);
    }
}

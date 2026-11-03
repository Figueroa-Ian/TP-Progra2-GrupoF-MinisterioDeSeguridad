
package com.PROGRA2.BACKEND.controller;


import com.PROGRA2.BACKEND.models.Delito;
import com.PROGRA2.BACKEND.repository.DelitoRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return delitoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delito> buscarPorId(@PathVariable Long id) {
        return delitoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Delito> crearDelito(@RequestBody Delito delito) {
        Delito nuevo = delitoRepository.save(delito);
        return ResponseEntity.ok(nuevo);
    }
}
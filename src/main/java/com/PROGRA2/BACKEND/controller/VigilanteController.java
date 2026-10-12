
package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Vigilante;
import com.PROGRA2.BACKEND.repository.VigilanteRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vigilantes")
public class VigilanteController {
   
    private final VigilanteRepository vigilanteRepository;

    public VigilanteController(VigilanteRepository vigilanteRepository) {
        this.vigilanteRepository = vigilanteRepository;
    }
    
    @GetMapping
    public List<Vigilante> listarTodos() {
        return vigilanteRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Vigilante> buscarPorId(@PathVariable Integer id) {
        return vigilanteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    
    
}

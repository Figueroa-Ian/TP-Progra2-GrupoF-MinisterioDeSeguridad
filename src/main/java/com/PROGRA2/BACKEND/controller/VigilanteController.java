
package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Vigilante;
import com.PROGRA2.BACKEND.repository.VigilanteRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @PostMapping("/crear")
    public Vigilante crearLVigilanteConParams(@RequestParam String legajo, @RequestParam int edad, @RequestParam boolean disponibleTrabajar) {
        Vigilante vig = new Vigilante();
        vig.setDisponibleTrabajar(disponibleTrabajar);
        vig.setEdad(edad);
        vig.setLegajo(legajo);
        // Aquí es donde realmente ocurre el INSERT
        return vigilanteRepository.save(vig);
        
    }
    
    
    
}

package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Delincuente;
import com.PROGRA2.BACKEND.services.IDelincuenteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/delincuentes")
public class DelincuenteController {

    private final IDelincuenteService delincuenteService;

    public DelincuenteController(IDelincuenteService delincuenteService) {
        this.delincuenteService = delincuenteService;
    }
    
    //GET http://localhost:8080/api/delincuentes
    @GetMapping
    public List<Delincuente> verDelincuentes() {
        return delincuenteService.traerTodosLosDelincuentes();
    }
    
    // GET http://localhost:8080/api/delincuentes/1
    @GetMapping("/{id}")
    public ResponseEntity<Delincuente> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(delincuenteService.traerDelincuentePorId(id));
    }
    
    // Buscar Delincuentes por ID de banda (numeroBanda)
    @GetMapping("/buscar/delincuentes/numeroBanda/{numeroBanda}")
    public List<Delincuente> verMiembrosPorIdBanda(@PathVariable Integer numeroBanda) {
        return delincuenteService.verMiembrosPorIdBanda(numeroBanda);
    }

    // Buscar Delincuentes por Nombre de banda
    @GetMapping("buscar/delincuentes/nombreBanda/{nombreBanda}")
    public List<Delincuente> verMiembrosPorNombreBanda(@PathVariable String nombreBanda) {
        return delincuenteService.verMiembrosPorNombreBanda(nombreBanda);
    }
    
    // POST http://localhost:8080/api/delincuentes
    @PostMapping
    public ResponseEntity<Delincuente> crearDelincuente(@RequestBody Delincuente delincuente) {
        Delincuente nuevoDelincuente = delincuenteService.guardarDelincuente(delincuente);
        return new ResponseEntity<>(nuevoDelincuente, HttpStatus.CREATED);
    }
    
    // PUT http://localhost:8080/api/delincuentes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Delincuente> modificarDelincuente(@PathVariable Integer id, @RequestBody Delincuente delincuenteDetalles) {
        Delincuente delincuenteActualizado = delincuenteService.modificarDelincuente(id, delincuenteDetalles);
        return ResponseEntity.ok(delincuenteActualizado);
    }
}
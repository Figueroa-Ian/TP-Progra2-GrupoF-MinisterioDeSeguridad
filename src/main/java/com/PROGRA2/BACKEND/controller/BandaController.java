package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Banda;
import com.PROGRA2.BACKEND.services.IBandaService;
import java.util.List;
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
@RequestMapping("/api/bandas")
public class BandaController {

    private final IBandaService bandaService;

    public BandaController(IBandaService bandaService) {
        this.bandaService = bandaService;
    }

    //GET http://localhost:8080/api/bandas
    @GetMapping
    public List<Banda> verBandas() {
        return bandaService.traerTodasLosBandas();
    }

    // GET http://localhost:8080/api/bandas/1
    @GetMapping("/{id}")
    public Banda verBandaPorId(@PathVariable Integer numeroBanda) {
        return bandaService.traerBandaPorId(numeroBanda);
    }

    @PostMapping("/crear/banda")
    public Banda crearBanda(@RequestBody Banda banda) {
        return bandaService.guardarBanda(banda);
    }

    // PUT http://localhost:8080/api/bandas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Banda> modificarBanda(@PathVariable Integer id, @RequestBody Banda delincuenteDetalles) {
        Banda delincuenteActualizado = bandaService.modificarBanda(id, delincuenteDetalles);
        return ResponseEntity.ok(delincuenteActualizado);
    }

    // 1. ENDPOINT PARA BÚSQUEDA PARCIAL (Containing)
    @GetMapping("/buscar/bandas/nombreParcial/{nombre}")
    public List<Banda> buscarBandaPorNombre(@PathVariable String nombre) {
        return bandaService.buscarBandaPorNombre(nombre);
    }

    // 2. ENDPOINT PARA BÚSQUEDA EXACTA
    @GetMapping("/buscar/bandas/nombreExacto/{nombre}")
    public List<Banda> buscarBandaPorNombreExacto(@PathVariable String nombre) {
        return bandaService.buscarBandaPorNombreExacto(nombre);
    }
}

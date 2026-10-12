package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Banda;
import com.PROGRA2.BACKEND.repository.BandaRepository;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class BandaController {

    private final BandaRepository bandaRepository;

    public BandaController(BandaRepository bandaRepository) {
        this.bandaRepository = bandaRepository;
    }

    @GetMapping("/buscar/bandas")///Traemos todas las bandas
    public List<Banda> verBandas() {
        return bandaRepository.findAll();
    }
    
    // Buscar por ID numérico
    @GetMapping("/buscar/bandas/numeroBanda/{numeroBanda}")
    public Banda verBandaPorId(@PathVariable Integer numeroBanda) {
        return bandaRepository.findById(numeroBanda).orElse(null);
    }

    // 1. ENDPOINT PARA BÚSQUEDA PARCIAL (Containing)
    @GetMapping("/buscar/bandas/nombreParcial/{nombre}")
    public List<Banda> buscarPorNombre(@PathVariable String nombre) {
        return bandaRepository.findByNombreBandaContainingIgnoreCase(nombre);
    }

    // 2. ENDPOINT PARA BÚSQUEDA EXACTA
    @GetMapping("/buscar/bandas/nombreExacto/{nombre}")
    public List<Banda> buscarPorNombreExacto(@PathVariable String nombre) {
        return bandaRepository.findByNombreBandaIgnoreCase(nombre);
    }
    
    
    @PostMapping("/crear/banda")
    public Banda crearBanda(@RequestBody Banda banda) {
        return bandaRepository.save(banda);
    }
}
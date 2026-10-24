package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Banda;
import com.PROGRA2.BACKEND.services.BandaService;
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

    private final BandaService bandaService;

    public BandaController(BandaService bandaService) {
        this.bandaService = bandaService;
    }

    @GetMapping("/buscar/bandas") // Traemos todas las bandas
    public List<Banda> verBandas() {
        return bandaService.verBandas();
    }

    // Buscar por ID numérico
    @GetMapping("/buscar/bandas/numeroBanda/{numeroBanda}")
    public Banda verBandaPorId(@PathVariable Integer numeroBanda) {
        return bandaService.verBandaPorId(numeroBanda);
    }

    // 1. ENDPOINT PARA BÚSQUEDA PARCIAL (Containing)
    @GetMapping("/buscar/bandas/nombreParcial/{nombre}")
    public List<Banda> buscarPorNombre(@PathVariable String nombre) {
        return bandaService.buscarPorNombre(nombre);
    }

    // 2. ENDPOINT PARA BÚSQUEDA EXACTA
    @GetMapping("/buscar/bandas/nombreExacto/{nombre}")
    public List<Banda> buscarPorNombreExacto(@PathVariable String nombre) {
        return bandaService.buscarPorNombreExacto(nombre);
    }

    @PostMapping("/crear/banda")
    public Banda crearBanda(@RequestBody Banda banda) {
        return bandaService.crearBanda(banda);
    }
}

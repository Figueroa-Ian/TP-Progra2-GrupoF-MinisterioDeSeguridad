package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Delincuente;
import com.PROGRA2.BACKEND.services.DelincuenteService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delincuentes")
public class DelincuenteController {

    private final DelincuenteService delincuenteService;

    public DelincuenteController(DelincuenteService delincuenteService) {
        this.delincuenteService = delincuenteService;
    }

    @GetMapping("/buscar/delincuentes")
    public List<Delincuente> verDelincuentes() {
        return delincuenteService.verDelincuentes();
    }

    // 1. Buscar Delincuentes por ID de banda (numeroBanda)
    @GetMapping("/buscar/delincuentes/numeroBanda/{numeroBanda}")
    public List<Delincuente> verMiembrosPorIdBanda(@PathVariable Integer numeroBanda) {
        return delincuenteService.verMiembrosPorIdBanda(numeroBanda);
    }

    // 2. Buscar Delincuentes por Nombre de banda
    @GetMapping("buscar/delincuentes/nombreBanda/{nombreBanda}")
    public List<Delincuente> verMiembrosPorNombreBanda(@PathVariable String nombreBanda) {
        return delincuenteService.verMiembrosPorNombreBanda(nombreBanda);
    }
}
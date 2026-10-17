
package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Banda;
import com.PROGRA2.BACKEND.models.Delincuente;
import com.PROGRA2.BACKEND.repository.BandaRepository;
import com.PROGRA2.BACKEND.repository.DelincuenteRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")

public class DelincuenteController {
    
    private final DelincuenteRepository delincuenteRepository;
    private final BandaRepository bandaRepository; // Lo inyectamos para poder buscar la banda por nombre e id
    
    public DelincuenteController(DelincuenteRepository delincuenteRepository, BandaRepository bandaRepository) {
        this.delincuenteRepository = delincuenteRepository;
        this.bandaRepository = bandaRepository;
    }
    
    @GetMapping("/buscar/delincuentes")
    public List<Delincuente> verDelincuentes() {
        return delincuenteRepository.findAll();
    }
    // 1. Buscar Delincuentes por ID de banda (numeroBanda)
    @GetMapping("/buscar/delincuentes/numeroBanda/{numeroBanda}")
    public List<Delincuente> verMiembrosPorIdBanda(@PathVariable Integer numeroBanda) {
        return delincuenteRepository.findByNumeroBanda(numeroBanda);
    }

    // 2. Buscar Delincuentes por Nombre de banda
    @GetMapping("buscar/delincuentes/nombreBanda/{nombreBanda}")
    public List<Delincuente> verMiembrosPorNombreBanda(@PathVariable String nombreBanda) {
        // Paso A: Buscamos las bandas que coincidan con el nombre ingresado
        List<Banda> bandasEncontradas = bandaRepository.findByNombreBandaContainingIgnoreCase(nombreBanda);
        
        List<Delincuente> todosLosMiembros = new ArrayList<>();
        
        // Paso B: Por cada banda encontrada, traemos sus delincuentes y los acumulamos
        for (Banda banda : bandasEncontradas) {
            List<Delincuente> miembrosDeEstaBanda = delincuenteRepository.findByNumeroBanda(banda.getNumeroBanda());
            todosLosMiembros.addAll(miembrosDeEstaBanda);
        }
        
        return todosLosMiembros;
    }
}
package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Banda;
import com.PROGRA2.BACKEND.models.Delincuente;
import com.PROGRA2.BACKEND.repository.BandaRepository;
import com.PROGRA2.BACKEND.repository.DelincuenteRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DelincuenteService {

    private final DelincuenteRepository delincuenteRepository;
    private final BandaRepository bandaRepository;

    public DelincuenteService(DelincuenteRepository delincuenteRepository, BandaRepository bandaRepository) {
        this.delincuenteRepository = delincuenteRepository;
        this.bandaRepository = bandaRepository;
    }

    public List<Delincuente> verDelincuentes() {
        return delincuenteRepository.findAll();
    }

    public List<Delincuente> verMiembrosPorIdBanda(Integer numeroBanda) {
        return delincuenteRepository.findByNumeroBanda(numeroBanda);
    }

    public List<Delincuente> verMiembrosPorNombreBanda(String nombreBanda) {
        List<Banda> bandasEncontradas = bandaRepository.findByNombreBandaContainingIgnoreCase(nombreBanda);
        
        List<Delincuente> todosLosMiembros = new ArrayList<>();
        
        // Paso B: Por cada banda encontrada, traemos sus delincuentes y los acumulamos
        for (Banda banda : bandasEncontradas) {
            List<Delincuente> miembrosDeEstaBanda = delincuenteRepository.findByNumeroBanda(banda.getNumeroBanda());
            todosLosMiembros.addAll(miembrosDeEstaBanda);
        }//TEST COMENTARIO
        
        return todosLosMiembros;
    }
}
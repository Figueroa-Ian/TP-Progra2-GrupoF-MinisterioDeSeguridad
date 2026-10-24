
package com.PROGRA2.BACKEND.services;
import com.PROGRA2.BACKEND.models.Banda;
import com.PROGRA2.BACKEND.repository.BandaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BandaService {

    private final BandaRepository bandaRepository;

    public BandaService(BandaRepository bandaRepository) {
        this.bandaRepository = bandaRepository;
    }

    public List<Banda> verBandas() {
        return bandaRepository.findAll();
    }
    
    public Banda verBandaPorId(Integer numeroBanda) {
        return bandaRepository.findById(numeroBanda).orElse(null);
    }

    public List<Banda> buscarPorNombre(String nombre) {
        return bandaRepository.findByNombreBandaContainingIgnoreCase(nombre);
    }

    public List<Banda> buscarPorNombreExacto(String nombre) {
        return bandaRepository.findByNombreBandaIgnoreCase(nombre);
    }
    
    public Banda crearBanda(Banda banda) {
        return bandaRepository.save(banda);
    }
}
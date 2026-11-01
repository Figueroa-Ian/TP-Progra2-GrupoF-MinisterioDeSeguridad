package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Banda;
import com.PROGRA2.BACKEND.repository.BandaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BandaService implements IBandaService {

    @Autowired
    private BandaRepository bandaRepository;

    @Override
    public List<Banda> traerTodasLosBandas() {
        return bandaRepository.findAll();
    }

    @Override
    public Banda traerBandaPorId(int numeroBanda) {
        return bandaRepository.findById(numeroBanda).orElse(null);
    }

    @Override
    public Banda guardarBanda(Banda banda) {
        return bandaRepository.save(banda);
    }

    @Override
    public void eliminarBanda(int id) {
        bandaRepository.deleteById(id);
    }

    @Override
    public Banda modificarBanda(Integer id, Banda bandaModificado) {
        // 1. Buscamos el banda existente en la base de datos
        Banda banda = traerBandaPorId(id);

        if (banda != null) {
            // 2. Actualizamos solo los atributos permitidos
            banda.setNumeroBanda(bandaModificado.getNumeroBanda());

            // 3. Guardamos los cambios manteniendo el ID original
            return bandaRepository.save(banda);
        }

        // Si no se encuentra, retornar null o lanzar una excepción
        return null;
    }

    @Override
    public List<Banda> buscarBandaPorNombre(String nombre) {
        return bandaRepository.findByNombreBandaContainingIgnoreCase(nombre);
    }

    @Override
    public List<Banda> buscarBandaPorNombreExacto(String nombre) {
        return bandaRepository.findByNombreBandaIgnoreCase(nombre);
    }
}

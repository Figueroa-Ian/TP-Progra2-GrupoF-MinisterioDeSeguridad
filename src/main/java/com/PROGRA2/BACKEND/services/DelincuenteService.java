package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Banda;
import com.PROGRA2.BACKEND.models.Delincuente;
import com.PROGRA2.BACKEND.models.Rol;
import com.PROGRA2.BACKEND.repository.BandaRepository;
import com.PROGRA2.BACKEND.repository.DelincuenteRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DelincuenteService implements IDelincuenteService {

    private final DelincuenteRepository delincuenteRepository;
    private final BandaRepository bandaRepository;

    public DelincuenteService(DelincuenteRepository delincuenteRepository, BandaRepository bandaRepository) {
        this.delincuenteRepository = delincuenteRepository;
        this.bandaRepository = bandaRepository;
    }

    @Override
    public List<Delincuente> traerTodosLosDelincuentes() {
        return delincuenteRepository.findAll();
    }

    @Override
    public Delincuente traerDelincuentePorId(int id) {
        return delincuenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delincuente no encontrado con ID: " + id));
    }

    @Override
    public Delincuente guardarDelincuente(Delincuente delincuente) {
        delincuente.setRol(Rol.DELINCUENTE);
        return delincuenteRepository.save(delincuente);
    }

    @Override
    public void eliminarDelincuente(int id) {
        delincuenteRepository.deleteById(id);
    }

    @Override
    public Delincuente modificarDelincuente(Integer id, Delincuente delincuenteModificado) {
        // 1. Buscamos el delincuente existente en la base de datos
        Delincuente delincuente = traerDelincuentePorId(id);

        if (delincuente != null) {
            // 2. Actualizamos solo los atributos permitidos (ejemplo: datos personales y de delincuente)
            delincuente.setNombre(delincuenteModificado.getNombre());
            delincuente.setApellido(delincuenteModificado.getApellido());
            delincuente.setDireccion(delincuenteModificado.getDireccion());
            delincuente.setFechaNacimiento(delincuenteModificado.getFechaNacimiento());
            delincuente.setClaveDelincuente(delincuenteModificado.getClaveDelincuente());
            delincuente.setNumeroBanda(delincuenteModificado.getNumeroBanda()); // O el atributo correspondiente a la banda

            // 3. Guardamos los cambios manteniendo el ID original
            return delincuenteRepository.save(delincuente);
        }

        // Si no se encuentra, retornar null o lanzar una excepción
        return null;
    }

    @Override
    public List<Delincuente> verMiembrosPorIdBanda(Integer numeroBanda) {
        return delincuenteRepository.findByNumeroBanda(numeroBanda);
    }

    @Override
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

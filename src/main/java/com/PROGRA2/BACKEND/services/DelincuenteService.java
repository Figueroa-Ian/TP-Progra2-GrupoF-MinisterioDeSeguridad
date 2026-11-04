package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Banda;
import com.PROGRA2.BACKEND.models.Delincuente;
import com.PROGRA2.BACKEND.models.Rol;
import com.PROGRA2.BACKEND.repository.BandaRepository;
import com.PROGRA2.BACKEND.repository.DelincuenteRepository;
import com.PROGRA2.BACKEND.repository.ExpedienteRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DelincuenteService implements IDelincuenteService {

    private final DelincuenteRepository delincuenteRepository;
    private final BandaRepository bandaRepository;
    private final ExpedienteRepository expedienteRepository;
    private final PersonaService personaService;

    public DelincuenteService(DelincuenteRepository delincuenteRepository, BandaRepository bandaRepository,
                              ExpedienteRepository expedienteRepository, PersonaService personaService) {
        this.delincuenteRepository = delincuenteRepository;
        this.bandaRepository = bandaRepository;
        this.expedienteRepository = expedienteRepository;
        this.personaService = personaService;
    }

    @Override
    public List<Delincuente> traerTodosLosDelincuentes() {
        return delincuenteRepository.findAll();
    }

    @Override
    public Delincuente traerDelincuentePorId(int id) {
        return delincuenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un delincuente con id " + id));
    }

    @Override
    public Delincuente guardarDelincuente(Delincuente delincuente) {
        personaService.validarPersonaNueva(delincuente, PersonaService.EDAD_MINIMA_DELINCUENTE);   // mayor de 14 años y DNI no repetido
        delincuente.setRol(Rol.DELINCUENTE);
        return delincuenteRepository.save(delincuente);
    }

    @Override
    @Transactional
    public void eliminarDelincuente(int id) {
        Delincuente existente = delincuenteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un delincuente con id " + id));

        // No se elimina si figura en algún expediente (quedaría una clave huérfana)
        String clave = existente.getClaveDelincuente();
        if (clave != null) {
            long enUso = expedienteRepository.contarPorDelincuente(clave);
            if (enUso > 0) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "No se puede eliminar el delincuente: figura en " + enUso + " expediente(s).");
            }
        }

        try {
            delincuenteRepository.delete(existente);
            delincuenteRepository.flush();   // fuerza el DELETE acá para detectar otros registros que dependan de él
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el delincuente: tiene registros asociados.");
        }
    }

    @Override
    public Delincuente modificarDelincuente(Integer id, Delincuente delincuenteModificado) {
        // 1. Buscamos el delincuente existente en la base de datos
        Delincuente delincuente = traerDelincuentePorId(id);
        personaService.validarFechaNacimiento(delincuenteModificado.getFechaNacimiento(), PersonaService.EDAD_MINIMA_DELINCUENTE);   // sigue siendo mayor de 14

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

        // Si no se encuentra, retornar null o lanzar una excepciÃ³n
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

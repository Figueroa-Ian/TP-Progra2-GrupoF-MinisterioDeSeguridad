package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Vigilante;
import com.PROGRA2.BACKEND.repository.VigilanteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VigilanteService implements IVigilanteService {

    private final VigilanteRepository vigilanteRepository;

    public VigilanteService(VigilanteRepository vigilanteRepository) {
        this.vigilanteRepository = vigilanteRepository;
    }

    @Override
    public List<Vigilante> traerTodosLosVigilantes() {
        return vigilanteRepository.findAll();
    }

    @Override
    public Vigilante traerVigilantePorId(Integer id) {
        return vigilanteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un vigilante con id " + id));
    }

    @Override
    @Transactional
    public Vigilante crearVigilante(String legajo, int edad, boolean disponibleTrabajar) {
        String legajoLimpio = validar(legajo, edad);
        verificarLegajoLibre(legajoLimpio, null);

        Vigilante vig = new Vigilante();
        vig.setLegajo(legajoLimpio);
        vig.setEdad(edad);
        vig.setDisponibleTrabajar(disponibleTrabajar);
        return vigilanteRepository.save(vig);   // acá ocurre el INSERT
    }

    @Override
    @Transactional
    public Vigilante guardarVigilante(Vigilante vigilante) {
        vigilante.setLegajo(validar(vigilante.getLegajo(), vigilante.getEdad()));
        verificarLegajoLibre(vigilante.getLegajo(), null);
        try {
            return vigilanteRepository.saveAndFlush(vigilante);   // INSERT de la persona y del vigilante
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se pudo registrar: el DNI ya existe o falta algún dato obligatorio de la persona.");
        }
    }

    @Override
    @Transactional
    public Vigilante modificarVigilante(Integer id, Vigilante detalles) {
        Vigilante existente = traerVigilantePorId(id);   // 404 si no existe

        String legajoLimpio = validar(detalles.getLegajo(), detalles.getEdad());
        verificarLegajoLibre(legajoLimpio, id);

        existente.setLegajo(legajoLimpio);
        existente.setEdad(detalles.getEdad());
        existente.setDisponibleTrabajar(detalles.isDisponibleTrabajar());
        return vigilanteRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminarVigilante(Integer id) {
        Vigilante existente = traerVigilantePorId(id);   // 404 si no existe
        try {
            vigilanteRepository.delete(existente);
            vigilanteRepository.flush();   // fuerza el DELETE acá para detectar registros que dependen de él
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el vigilante: tiene registros asociados (por ejemplo, contrataciones).");
        }
    }

    // ------------------------------------------------------------------
    // Validaciones (devuelven 400 / 409 en vez de un 500 de la base)
    // ------------------------------------------------------------------
    private String validar(String legajo, int edad) {
        if (legajo == null || legajo.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El legajo es obligatorio.");
        }
        String limpio = legajo.trim();
        if (limpio.length() > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El legajo no puede superar los 20 caracteres.");
        }
        if (edad <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La edad debe ser mayor que cero.");
        }
        return limpio;
    }

    // idPropio: al modificar, el vigilante puede conservar su propio legajo
    private void verificarLegajoLibre(String legajo, Integer idPropio) {
        Optional<Vigilante> otro = vigilanteRepository.findByLegajo(legajo);
        if (otro.isPresent() && !otro.get().getId().equals(idPropio)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un vigilante con el legajo " + legajo + ".");
        }
    }
}
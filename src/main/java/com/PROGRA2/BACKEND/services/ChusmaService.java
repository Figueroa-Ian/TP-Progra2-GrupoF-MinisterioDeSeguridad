package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Persona;
import com.PROGRA2.BACKEND.models.Rol;
import com.PROGRA2.BACKEND.repository.PersonaRepository;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Un chusma es una Persona con rol INVESTIGADOR (no tiene tabla propia).
 */
@Service
public class ChusmaService implements IChusmaService {

    private final PersonaRepository personaRepository;
    private final PersonaService personaService;

    public ChusmaService(PersonaRepository personaRepository, PersonaService personaService) {
        this.personaRepository = personaRepository;
        this.personaService = personaService;
    }

    @Override
    public List<Persona> traerTodosLosChusmas() {
        return personaRepository.findByRol(Rol.INVESTIGADOR);
    }

    @Override
    public Persona traerChusmaPorId(Integer id) {
        return personaRepository.findById(id)
                .filter(p -> p.getRol() == Rol.INVESTIGADOR)   // un juez o vigilante no es un chusma
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un chusma con id " + id));
    }

    @Override
    @Transactional
    public Persona guardarChusma(Persona chusma) {
        chusma.setId(null);                          // siempre es un alta
        validarDatosBasicos(chusma);
        personaService.validarPersonaNueva(chusma);  // mayor de 18 y DNI no repetido
        chusma.setRol(Rol.INVESTIGADOR);
        return personaRepository.save(chusma);
    }

    @Override
    @Transactional
    public Persona modificarChusma(Integer id, Persona detalles) {
        Persona existente = traerChusmaPorId(id);    // 404 si no existe
        validarDatosBasicos(detalles);
        personaService.validarFechaNacimiento(detalles.getFechaNacimiento());

        // Si cambia el DNI, no puede coincidir con el de otra persona
        String dniNuevo = detalles.getDni() == null ? "" : detalles.getDni().trim();
        if (dniNuevo.isEmpty()) {
            throw datoInvalido("El DNI es obligatorio.");
        }
        if (!dniNuevo.equals(existente.getDni())) {
            if (personaRepository.existsByDni(dniNuevo)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Ya existe una persona registrada con el DNI " + dniNuevo + ".");
            }
            existente.setDni(dniNuevo);
        }

        existente.setNombre(detalles.getNombre().trim());
        existente.setApellido(detalles.getApellido().trim());
        existente.setDireccion(detalles.getDireccion() == null ? null : detalles.getDireccion().trim());
        existente.setFechaNacimiento(detalles.getFechaNacimiento());
        return personaRepository.save(existente);          // el rol no se toca
    }

    @Override
    @Transactional
    public void eliminarChusma(Integer id) {
        Persona existente = traerChusmaPorId(id);    // 404 si no existe
        try {
            personaRepository.delete(existente);
            personaRepository.flush();   // fuerza el DELETE acá para detectar registros que dependan de él
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el chusma: tiene un usuario u otros registros asociados.");
        }
    }

    private void validarDatosBasicos(Persona p) {
        if (estaVacio(p.getNombre())) throw datoInvalido("El nombre es obligatorio.");
        if (estaVacio(p.getApellido())) throw datoInvalido("El apellido es obligatorio.");
        p.setNombre(p.getNombre().trim());
        p.setApellido(p.getApellido().trim());
    }

    private boolean estaVacio(String t) {
        return t == null || t.trim().isEmpty();
    }

    private ResponseStatusException datoInvalido(String mensaje) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, mensaje);
    }
}

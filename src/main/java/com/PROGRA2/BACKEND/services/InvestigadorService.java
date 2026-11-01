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
 * Un investigador es una Persona con rol INVESTIGADOR (no tiene tabla propia).
 */
@Service
public class InvestigadorService implements IInvestigadorService {

    private final PersonaRepository personaRepository;
    private final PersonaService personaService;

    public InvestigadorService(PersonaRepository personaRepository, PersonaService personaService) {
        this.personaRepository = personaRepository;
        this.personaService = personaService;
    }

    @Override
    public List<Persona> traerTodosLosInvestigadores() {
        return personaRepository.findByRol(Rol.INVESTIGADOR);
    }

    @Override
    public Persona traerInvestigadorPorId(Integer id) {
        return personaRepository.findById(id)
                .filter(p -> p.getRol() == Rol.INVESTIGADOR)   // un juez o vigilante no es un investigador
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un investigador con id " + id));
    }

    @Override
    @Transactional
    public Persona guardarInvestigador(Persona investigador) {
        investigador.setId(null);                          // siempre es un alta
        validarDatosBasicos(investigador);
        personaService.validarPersonaNueva(investigador);  // mayor de 18 y DNI no repetido
        investigador.setRol(Rol.INVESTIGADOR);
        return personaRepository.save(investigador);
    }

    @Override
    @Transactional
    public Persona modificarInvestigador(Integer id, Persona detalles) {
        Persona existente = traerInvestigadorPorId(id);    // 404 si no existe
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
    public void eliminarInvestigador(Integer id) {
        Persona existente = traerInvestigadorPorId(id);    // 404 si no existe
        try {
            personaRepository.delete(existente);
            personaRepository.flush();   // fuerza el DELETE acá para detectar registros que dependan de él
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el investigador: tiene un usuario u otros registros asociados.");
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

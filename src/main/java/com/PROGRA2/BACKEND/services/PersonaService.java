package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Persona;
import com.PROGRA2.BACKEND.repository.PersonaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PersonaService {

    public static final int EDAD_MINIMA = 18;
    public static final int EDAD_MINIMA_DELINCUENTE = 14;   // solo los delincuentes pueden registrarse desde los 14

    @Autowired
    private PersonaRepository pR;

    public List<Persona> listarPersonas() {
        System.out.println("Se listan las personas a traves del nuevo metodo Service");
        return pR.findAll();
    }

    // ------------------------------------------------------------------
    // Validaciones para registrar personas (delincuentes, jueces, vigilantes...)
    // Las usan los services de cada rol ANTES de guardar.
    // ------------------------------------------------------------------

    /** Valida fecha de nacimiento (mayor de edad) y DNI (obligatorio y no repetido). */
    public void validarPersonaNueva(Persona persona) {
        validarPersonaNueva(persona, EDAD_MINIMA);
    }

    /** Igual que el anterior, pero con una edad mínima propia (ej: delincuentes, 14). */
    public void validarPersonaNueva(Persona persona, int edadMinima) {
        validarFechaNacimiento(persona.getFechaNacimiento(), LocalDate.now(), edadMinima);
        validarDniNuevo(persona);
    }

    /** Mayor de EDAD_MINIMA años según la fecha de hoy. */
    public void validarFechaNacimiento(LocalDate fechaNacimiento) {
        validarFechaNacimiento(fechaNacimiento, LocalDate.now(), EDAD_MINIMA);
    }

    public void validarFechaNacimiento(LocalDate fechaNacimiento, int edadMinima) {
        validarFechaNacimiento(fechaNacimiento, LocalDate.now(), edadMinima);
    }

    // Se separa "hoy" para poder probarlo con cualquier fecha
    public void validarFechaNacimiento(LocalDate fechaNacimiento, LocalDate hoy) {
        validarFechaNacimiento(fechaNacimiento, hoy, EDAD_MINIMA);
    }

    public void validarFechaNacimiento(LocalDate fechaNacimiento, LocalDate hoy, int edadMinima) {
        if (fechaNacimiento == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de nacimiento es obligatoria.");
        }
        if (fechaNacimiento.isAfter(hoy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de nacimiento no puede ser futura.");
        }
        // Tiene que ser, como máximo, el mismo día de hace "edadMinima" años
        LocalDate fechaLimite = hoy.minusYears(edadMinima);
        if (fechaNacimiento.isAfter(fechaLimite)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La persona debe tener al menos " + edadMinima + " años (nacida el " + fechaLimite + " o antes).");
        }
    }

    /** El DNI es obligatorio y no puede estar registrado en otra persona. */
    public void validarDniNuevo(Persona persona) {
        String dni = persona.getDni();
        if (dni == null || dni.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El DNI es obligatorio.");
        }
        String limpio = dni.trim();
        persona.setDni(limpio);   // se guarda sin espacios sobrantes

        if (pR.existsByDni(limpio)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una persona registrada con el DNI " + limpio + ".");
        }
    }

    // El service tiene las funciones del CRUD.. falta crear, modif, eliminar..
}

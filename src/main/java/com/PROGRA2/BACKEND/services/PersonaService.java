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
        validarFechaNacimiento(persona.getFechaNacimiento());
        validarDniNuevo(persona);
    }

    /** Mayor de EDAD_MINIMA años según la fecha de hoy. */
    public void validarFechaNacimiento(LocalDate fechaNacimiento) {
        validarFechaNacimiento(fechaNacimiento, LocalDate.now());
    }

    // Se separa "hoy" para poder probarlo con cualquier fecha
    public void validarFechaNacimiento(LocalDate fechaNacimiento, LocalDate hoy) {
        if (fechaNacimiento == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de nacimiento es obligatoria.");
        }
        if (fechaNacimiento.isAfter(hoy)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de nacimiento no puede ser futura.");
        }
        // Tiene que ser, como máximo, el mismo día de hace 18 años (nacido ayer, o hace 17 años, no vale)
        LocalDate fechaLimite = hoy.minusYears(EDAD_MINIMA);
        if (fechaNacimiento.isAfter(fechaLimite)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La persona debe tener al menos " + EDAD_MINIMA + " años (nacida el " + fechaLimite + " o antes).");
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

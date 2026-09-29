package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Persona;
import com.PROGRA2.BACKEND.models.Rol;
import com.PROGRA2.BACKEND.repository.PersonaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class PersonaController {

    private final PersonaRepository personaRepository;

    public PersonaController(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    //toma la ruta que le asignemos
    @GetMapping("/buscarpersona")
    // con RequestParam podemos asignar valores a la URL para poder consultar por un campo 
    // en especifico (ID) guardando el valor retornado en un string "id"
    // ej: //localhost:8080/buscarpersona?nombre=Lucas
    //RequestParam es para la URL
    //(asignamos el NOMBRE en la URL, ademas le indicamos si es necesario buscar por ese campo)
    //despues asignamos el nombre del campo que se va a obtener por URL
    public List<Persona> buscarPersona(@RequestParam(name = "nombre", required = false) String nombre) {
        // si NO es NULL Y NO esta VACIO
        // trim .. junta todo eliminando los espacios
        // isEmpity te dice si esta o no vacío
        if (nombre != null && !nombre.trim().isEmpty()) {
            return personaRepository.findByNombre(nombre.trim());
        }

        //devuelve todo o devuelve nada
        return personaRepository.findAll();

        //
    }

    //Crear a traves de un formulario HTML..
    //Hace un INSERT a la tabla y crea el objeto en la tabla
    @PostMapping("/crear")
    public Persona crearPersona(
            @RequestParam String dni,
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String direccion,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
            @RequestParam Rol rol,
            @RequestParam(required = false) Integer aniosAntiguedad,
            @RequestParam(required = false) Integer codigoBanda,
            @RequestParam(required = false) Integer legajo,
            @RequestParam(required = false, defaultValue = "false") Boolean disponibilidad
    ) {
        Persona persona;
        
        /*
        switch (rol) {
            case JUEZ:
                Juez juez = new Juez();
                if (aniosAntiguedad != null) {
                    juez.setAniosAntiguedad(aniosAntiguedad);
                }
                persona = juez;
                break;

            case DELINCUENTE:
                Delincuente delincuente = new Delincuente();
                if (codigoBanda != null) {
                    delincuente.setCodigoBanda(codigoBanda);
                }
                persona = delincuente;
                break;

            case VIGILANTE:
                Vigilante vigilante = new Vigilante();
                if (legajo != null) {
                    vigilante.setLegajo(legajo);
                }
                vigilante.setDisponibilidad(disponibilidad != null ? disponibilidad : false);
                persona = vigilante;
                break;

            default:
        */
                persona = new Persona();
                /*
                break;
        }*/
                
        // Datos comunes de Persona
        persona.setDni(dni);
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setDireccion(direccion);
        System.out.println("LA FECHA DE NAC ES: "+fechaNacimiento);
        persona.setFechaNacimiento(fechaNacimiento);
        persona.setRol(rol);

        return personaRepository.save(persona);
    }

    /*
    En lugar de recibir cada parámetro por separado con @RequestParam y llamar a los 
    setters uno por uno, Spring Boot te permite mapear todos los campos del formulario 
    automáticamente a un objeto Persona
    
    @PostMapping("/crear")
    public Persona crearPersona(@ModelAttribute Persona persona) {
    return personaRepository.save(persona);
    }
     */
}

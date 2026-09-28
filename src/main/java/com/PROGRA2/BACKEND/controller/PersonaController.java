package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Persona;
import com.PROGRA2.BACKEND.repository.PersonaRepository;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}

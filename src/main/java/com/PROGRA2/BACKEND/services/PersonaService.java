package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Persona;
import com.PROGRA2.BACKEND.repository.PersonaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    
    @Autowired
    private PersonaRepository pR;
    
    public List<Persona> listarPersonas() {
        System.out.println("Se listan las personas a traves del nuevo metodo Service");
        return pR.findAll();
    }

    // El service tiene las funciones del CRUD.. falta crear, modif, eliminar..
}

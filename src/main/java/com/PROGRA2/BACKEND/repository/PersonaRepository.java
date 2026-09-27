package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Persona;
import com.PROGRA2.BACKEND.models.Rol;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
    //JPA utiliza el nombre del metodo para buscar por el campo de la tabla
    //por lo que es necesario indicarle el nombre del campo
    List<Persona> findByNombre(String nombre);
    List<Persona> findByRol(Rol rol);
}

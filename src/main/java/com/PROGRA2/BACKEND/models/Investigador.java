
package com.PROGRA2.BACKEND.models;

import java.time.LocalDate;

public class Investigador extends Persona{
    
    public Investigador(Integer id, String dni, String nombre, String apellido, String direccion, LocalDate fechaNacimiento, Rol rol) {
        super(id, dni, nombre, apellido, direccion, fechaNacimiento, rol);
    }

    public Investigador() {
    }

}

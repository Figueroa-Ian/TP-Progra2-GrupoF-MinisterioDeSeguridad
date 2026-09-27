/*
package com.PROGRA2.BACKEND.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //genera automat. por el LOMBOK.
@Entity
@Table(name = "vigilantes")
@PrimaryKeyJoinColumn(name = "persona_id") // Se vincula con el id de Persona
public class Vigilante extends Persona {

    @Column(nullable = false, length = 20)
    private String legajo;

    @Column(nullable = false)
    private int edad;

    public Vigilante() {
        super();
    }

    public Vigilante(String dni, String nombre, String apellido, String direccion, String legajo, int edad) {
        super(dni, nombre, apellido, direccion, Rol.VIGILANTE);
        this.legajo = legajo;
        this.edad = edad;
    }

}
*/
package com.PROGRA2.BACKEND.models;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "delincuentes")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Delincuente extends Persona {

    public Delincuente() {
        super();
    }

    public Delincuente(String dni, String nombre, String apellido, String direccion) {
        super(dni, nombre, apellido, direccion, Rol.DELINCUENTE);
    }
}

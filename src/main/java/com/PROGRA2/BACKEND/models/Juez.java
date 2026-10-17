
package com.PROGRA2.BACKEND.models;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDate;
import lombok.*;

@Getter            // Genera todos los getId(), getDni(), etc.
@Setter
@Entity
@Table(name = "jueces")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Juez extends Persona{
    @Column(name = "clave_juzgado", nullable = false, length = 30)
    private String claveJuzgado;
    @Column(nullable = false)
    private int antiguedadServicio;

    public Juez() {
    }

    public Juez(Integer id, String dni, String nombre, String apellido, String direccion, LocalDate fechaNacimiento, String claveJuzgado, int antiguedadServicio) {
        super(id, dni, nombre, apellido, direccion, fechaNacimiento, Rol.JUEZ);
        this.claveJuzgado = claveJuzgado;
        this.antiguedadServicio = antiguedadServicio;
    }
}

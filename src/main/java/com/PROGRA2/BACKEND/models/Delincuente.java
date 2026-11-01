package com.PROGRA2.BACKEND.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //genera automat. por el LOMBOK.
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "delincuentes")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Delincuente extends Persona{
    
    @Column(name = "clave")
    private String claveDelincuente; // Ej: "DEL-001"
    
    @Column(name = "numero_banda")
    private Integer numeroBanda;
    
    
    public Delincuente() {
        super();
    }
    
    public Delincuente(Integer id, String dni, String nombre, String apellido, String direccion, LocalDate fechaNacimiento, Integer numeroBanda) {
        super(id, dni, nombre, apellido, direccion, fechaNacimiento, Rol.DELINCUENTE);
        this.numeroBanda = numeroBanda;
    }

    public Delincuente(Integer id, String dni, String nombre, String apellido, String direccion, LocalDate fechaNacimiento) {
        super(id, dni, nombre, apellido, direccion,fechaNacimiento, Rol.DELINCUENTE);
    }

    public void setCodigoBanda(Integer codigoBanda) {
        this.numeroBanda = codigoBanda;
    }
}

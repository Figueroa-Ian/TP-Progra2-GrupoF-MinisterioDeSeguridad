
package com.PROGRA2.BACKEND.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //genera automat. por el LOMBOK.
@Entity
@Table(name = "delincuentes")
@PrimaryKeyJoinColumn(name = "persona_id") // Se vincula con el id de Persona

public class Delincuente extends Persona{
    
    @Column(name = "numero_banda")
    private Integer numeroBanda;
   
    
    public Delincuente() {
        super();
    }
    
    public Delincuente(Integer id, String dni, String nombre, String apellido, String direccion, LocalDate fechaNacimiento, Integer numeroBanda) {
        super(id, dni, nombre, apellido, direccion, fechaNacimiento, Rol.DELINCUENTE);
        this.numeroBanda = numeroBanda;
    }

}

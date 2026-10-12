
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
@Table(name = "delincuentes")
@PrimaryKeyJoinColumn(name = "persona_id") // Se vincula con el id de Persona

public class Delincuente extends Persona{
    
    @Column(name = "numero_banda")
    private Integer numeroBanda;
   
    
    public Delincuente() {
        super();
    }
    
    public Delincuente(String dni, String nombre, String apellido, String direccion, Integer numeroBanda) {
        super(dni, nombre, apellido, direccion, Rol.DELINCUENTE);
        this.numeroBanda = numeroBanda;
    }

}

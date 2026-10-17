package com.PROGRA2.BACKEND.models;

<<<<<<< HEAD
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
=======
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
>>>>>>> cd1e7a49c40530afef49ba00f62c7a291c6133a7

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
<<<<<<< HEAD
=======
    }
    
    public Delincuente(Integer id, String dni, String nombre, String apellido, String direccion, LocalDate fechaNacimiento, Integer numeroBanda) {
        super(id, dni, nombre, apellido, direccion, fechaNacimiento, Rol.DELINCUENTE);
        this.numeroBanda = numeroBanda;
>>>>>>> cd1e7a49c40530afef49ba00f62c7a291c6133a7
    }

    public Delincuente(String dni, String nombre, String apellido, String direccion) {
        super(dni, nombre, apellido, direccion, Rol.DELINCUENTE);
    }
}

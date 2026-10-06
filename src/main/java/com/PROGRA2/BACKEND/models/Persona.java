package com.PROGRA2.BACKEND.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //genera automat. por el LOMBOK.
@Entity
@Table(name = "personas") // le decimos que pertenece a esa tabla BDD
@Inheritance(strategy = InheritanceType.JOINED)// para que pueda hacer la busqueda con joined
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    
    // se le agregan estas anotaciones porque JPA interpreta el ENUM como int en ves de String
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Rol rol;

    public Persona() {
    }

    public Persona(String dni, String nombre, String apellido, String direccion, Rol rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.rol = rol;
    }
}

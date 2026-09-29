package com.PROGRA2.BACKEND.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter //genera automat. por el LOMBOK.
@Entity
@Table(name = "personas") // le decimos que pertenece a esa tabla BDD
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    // Nuevo campo: Fecha de Nacimiento
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    // se le agregan estas anotaciones porque JPA interpreta el ENUM como int en ves de String
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Rol rol;

    public Persona() {
    }

    public Persona(Integer id, String dni, String nombre, String apellido, String direccion, LocalDate fechaNacimiento, Rol rol) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.rol = rol;
    }
}

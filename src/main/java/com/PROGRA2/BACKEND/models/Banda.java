
package com.PROGRA2.BACKEND.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//@Getter
@Setter //genera automat. por el LOMBOK.
@Entity
@Table(name = "bandas")

public class Banda {
    @Id
    @Column(name = "numero_banda")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer numeroBanda;
    
    @Column(name = "numero_miembros")
    private int numeroMiembros = 0;
    
    @Column(name = "nombre_banda")
    private String nombreBanda;

    public Banda() {
    }

    public Banda(String nombreBanda) {
        this.numeroMiembros = 0;
        this.nombreBanda = nombreBanda;
    }

    public Integer getNumeroBanda() {
        return numeroBanda;
    }

    public int getNumeroMiembros() {
        return numeroMiembros;
    }

    public String getNombreBanda() {
        return nombreBanda;
    }
    
}

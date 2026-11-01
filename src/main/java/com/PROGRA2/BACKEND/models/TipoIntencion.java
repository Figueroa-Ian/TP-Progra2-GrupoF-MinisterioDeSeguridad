package com.PROGRA2.BACKEND.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_intencion")
public class TipoIntencion implements ITipoIntencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre; // Ej: dolosa, culposos, preterintencionales
    private String descripcion;
    private int modificadorCondena; // Años que suma/resta según la intención

    @Override
    public int obtenerAnosCondena() {
        return this.modificadorCondena;
    }

    @Override
    public String obtenerDescripcion() {
        return this.descripcion;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setModificadorCondena(int modificadorCondena) { this.modificadorCondena = modificadorCondena; }
}
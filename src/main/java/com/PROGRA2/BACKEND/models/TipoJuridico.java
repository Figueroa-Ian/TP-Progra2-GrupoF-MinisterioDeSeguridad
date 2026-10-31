package com.PROGRA2.BACKEND.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_juridico")
public class TipoJuridico implements ITipoJuridico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre; // Ej: contraPersona, contraPropiedad
    private String descripcion;
    private int anosCondenaBase;

    @Override
    public int obtenerAnosCondena() {
        return this.anosCondenaBase;
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
    public void setAnosCondenaBase(int anosCondenaBase) { this.anosCondenaBase = anosCondenaBase; }
}
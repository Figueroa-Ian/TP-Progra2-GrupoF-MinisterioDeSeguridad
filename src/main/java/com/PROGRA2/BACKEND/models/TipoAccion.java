package com.PROGRA2.BACKEND.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tipo_accion")
public class TipoAccion implements ITipoAccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre; // Ej: accion, omision
    private String descripcion;
    private int anosCondenaExtra;

    @Override
    public int obtenerAnosCondena() {
        return this.anosCondenaExtra;
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
    public void setAnosCondenaExtra(int anosCondenaExtra) { this.anosCondenaExtra = anosCondenaExtra; }
}
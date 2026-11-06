
package com.PROGRA2.BACKEND.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "delitos")
public class Delito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo; // Ej: Robo, Homicidio, Fraude

    @Column(length = 500)
    private String descripcion;

    @Column(name = "id_tipo_juridico")
    private Integer idTipoJuridico;

    @Column(name = "id_tipo_accion")
    private Integer idTipoAccion;

    @Column(name = "id_tipo_intencion")
    private Integer idTipoIntencion;

    public Delito() {}

    public Delito(String tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getIdTipoJuridico() { return idTipoJuridico; }
    public void setIdTipoJuridico(Integer idTipoJuridico) { this.idTipoJuridico = idTipoJuridico; }

    public Integer getIdTipoAccion() { return idTipoAccion; }
    public void setIdTipoAccion(Integer idTipoAccion) { this.idTipoAccion = idTipoAccion; }

    public Integer getIdTipoIntencion() { return idTipoIntencion; }
    public void setIdTipoIntencion(Integer idTipoIntencion) { this.idTipoIntencion = idTipoIntencion; }
}

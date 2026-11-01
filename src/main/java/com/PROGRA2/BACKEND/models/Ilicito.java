package com.PROGRA2.BACKEND.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ilicitos")
public class Ilicito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "anos_condena", nullable = false)
    private Integer anosCondena;

    @Column(name = "id_tipo_juridico")
    private Integer idTipoJuridico;

    @Column(name = "id_tipo_accion")
    private Integer idTipoAccion;

    @Column(name = "id_tipo_intencion")
    private Integer idTipoIntencion;

    public Ilicito() {
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getAnosCondena() { return anosCondena; }
    public void setAnosCondena(Integer anosCondena) { this.anosCondena = anosCondena; }

    public Integer getIdTipoJuridico() { return idTipoJuridico; }
    public void setIdTipoJuridico(Integer idTipoJuridico) { this.idTipoJuridico = idTipoJuridico; }

    public Integer getIdTipoAccion() { return idTipoAccion; }
    public void setIdTipoAccion(Integer idTipoAccion) { this.idTipoAccion = idTipoAccion; }

    public Integer getIdTipoIntencion() { return idTipoIntencion; }
    public void setIdTipoIntencion(Integer idTipoIntencion) { this.idTipoIntencion = idTipoIntencion; }
}
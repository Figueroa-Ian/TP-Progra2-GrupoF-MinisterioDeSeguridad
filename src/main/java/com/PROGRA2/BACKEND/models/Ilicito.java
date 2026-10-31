package com.PROGRA2.BACKEND.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ilicitos")
public class Ilicito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "anos_condena", nullable = false)
    private Integer anosCondena;

    // Relación con TipoJuridico
    @ManyToOne
    @JoinColumn(name = "id_tipo_juridico")
    private TipoJuridico tipoJuridico;

    // Relación con TipoAccion
    @ManyToOne
    @JoinColumn(name = "id_tipo_accion")
    private TipoAccion tipoAccion;

    // Relación con TipoIntencion
    @ManyToOne
    @JoinColumn(name = "id_tipo_intencion")
    private TipoIntencion tipoIntencion;

    // Constructores
    public Ilicito() {}

    public Ilicito(String descripcion, Integer anosCondena, TipoJuridico tipoJuridico, TipoAccion tipoAccion, TipoIntencion tipoIntencion) {
        this.descripcion = descripcion;
        this.anosCondena = anosCondena;
        this.tipoJuridico = tipoJuridico;
        this.tipoAccion = tipoAccion;
        this.tipoIntencion = tipoIntencion;
    }

    // Métodos del diagrama UML
    public int calcularAnosCondenaTotal() {
        int adicionales = 0;
        
        // Sumar penalizaciones según los comportamientos o estrategias de los tipos
        if (tipoJuridico != null) adicionales += tipoJuridico.obtenerAnosCondena();
        if (tipoAccion != null) adicionales += tipoAccion.obtenerAnosCondena();
        if (tipoIntencion != null) adicionales += tipoIntencion.obtenerAnosCondena();

        return this.anosCondena + adicionales;
    }

    public String obtenerDescripcionCompleta() {
        return String.format("%s - Condena base: %d años | Jurídico: %s | Acción: %s | Intención: %s",
                descripcion,
                anosCondena,
                tipoJuridico != null ? tipoJuridico.obtenerDescripcion() : "N/A",
                tipoAccion != null ? tipoAccion.obtenerDescripcion() : "N/A",
                tipoIntencion != null ? tipoIntencion.obtenerDescripcion() : "N/A");
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getAnosCondena() { return anosCondena; }
    public void setAnosCondena(Integer anosCondena) { this.anosCondena = anosCondena; }

    public TipoJuridico getTipoJuridico() { return tipoJuridico; }
    public void setTipoJuridico(TipoJuridico tipoJuridico) { this.tipoJuridico = tipoJuridico; }

    public TipoAccion getTipoAccion() { return tipoAccion; }
    public void setTipoAccion(TipoAccion tipoAccion) { this.tipoAccion = tipoAccion; }

    public TipoIntencion getTipoIntencion() { return tipoIntencion; }
    public void setTipoIntencion(TipoIntencion tipoIntencion) { this.tipoIntencion = tipoIntencion; }
}
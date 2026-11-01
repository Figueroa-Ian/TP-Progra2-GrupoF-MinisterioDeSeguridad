package com.PROGRA2.BACKEND.models;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Expediente (tabla "expedientes").
 * Sucursal y juez se guardan por su código/clave. Los delincuentes son UNO O VARIOS:
 * sus claves van en la tabla "expediente_delincuentes".
 * El ilícito sí es una relación: cada expediente tiene UN ilícito.
 */
@Entity
@Table(name = "expedientes")
public class Expediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_expediente")
    private Integer nroExpediente;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "codigo_sucursal", nullable = false)
    private Integer codigoSucursal;

    @Column(name = "clave_juez", nullable = false, length = 20)
    private String claveJuez;

    // Uno o varios delincuentes (tabla expediente_delincuentes: nro_expediente, clave_delincuente)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "expediente_delincuentes",
            joinColumns = @JoinColumn(name = "nro_expediente"))
    @Column(name = "clave_delincuente", nullable = false, length = 20)
    private List<String> clavesDelincuentes = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ilicito", nullable = false)
    private Ilicito ilicito;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "es_culpable", nullable = false)
    private Boolean esCulpable = false;

    @Column(name = "tiene_condena", nullable = false)
    private Boolean tieneCondena = false;

    @Column(name = "anos_condena", nullable = false)
    private Integer anosCondena = 0;

    @Column(name = "anos_vigencia_condena", nullable = false)
    private Integer anosVigenciaCondena = 0;

    @Column(name = "condena_vigente", nullable = false)
    private Boolean condenaVigente = false;

    public Expediente() {
    }

    public Integer getNroExpediente() { return nroExpediente; }
    public void setNroExpediente(Integer nroExpediente) { this.nroExpediente = nroExpediente; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Integer getCodigoSucursal() { return codigoSucursal; }
    public void setCodigoSucursal(Integer codigoSucursal) { this.codigoSucursal = codigoSucursal; }

    public String getClaveJuez() { return claveJuez; }
    public void setClaveJuez(String claveJuez) { this.claveJuez = claveJuez; }

    public List<String> getClavesDelincuentes() { return clavesDelincuentes; }
    public void setClavesDelincuentes(List<String> clavesDelincuentes) { this.clavesDelincuentes = clavesDelincuentes; }

    public Ilicito getIlicito() { return ilicito; }
    public void setIlicito(Ilicito ilicito) { this.ilicito = ilicito; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Boolean getEsCulpable() { return esCulpable; }
    public void setEsCulpable(Boolean esCulpable) { this.esCulpable = esCulpable; }

    public Boolean getTieneCondena() { return tieneCondena; }
    public void setTieneCondena(Boolean tieneCondena) { this.tieneCondena = tieneCondena; }

    public Integer getAnosCondena() { return anosCondena; }
    public void setAnosCondena(Integer anosCondena) { this.anosCondena = anosCondena; }

    public Integer getAnosVigenciaCondena() { return anosVigenciaCondena; }
    public void setAnosVigenciaCondena(Integer anosVigenciaCondena) { this.anosVigenciaCondena = anosVigenciaCondena; }

    public Boolean getCondenaVigente() { return condenaVigente; }
    public void setCondenaVigente(Boolean condenaVigente) { this.condenaVigente = condenaVigente; }
}

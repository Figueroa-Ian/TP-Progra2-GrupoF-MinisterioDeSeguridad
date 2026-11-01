
package com.PROGRA2.BACKEND.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "contrataciones")
public class Contratacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contrato")
    private Integer idContrato;

    @Column(name = "codigo_sucursal")
    private Integer codigoSucursal;

    @Column(name = "codigo_vigilante")
    private String codigoVigilante;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "con_arma")
    private boolean conArma;

    @Column(name = "observaciones")
    private String observaciones;

    public Contratacion() {
    }

    public Contratacion(Integer codigoSucursal, String codigoVigilante, LocalDate fecha, boolean conArma, String observaciones) {
        this.codigoSucursal = codigoSucursal;
        this.codigoVigilante = codigoVigilante;
        this.fecha = fecha;
        this.conArma = conArma;
        this.observaciones = observaciones;
    }
}
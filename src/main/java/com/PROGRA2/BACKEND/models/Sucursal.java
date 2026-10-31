
package com.PROGRA2.BACKEND.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //genera automat. por el LOMBOK.
@Entity
@Table(name = "sucursales")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "codigo_entidad")
    private String codigoEntidad;
    @Column(name = "numero_empleados")
    private Integer cantEmpleados;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "es_central")
    private boolean esCentral;

    public Sucursal() {
    }

    public Sucursal(Integer codigoSucursal, String codigoEntidad, Integer cantEmpleados, String direccion, boolean esCentral) {
        this.id = codigoSucursal;
        this.codigoEntidad = codigoEntidad;
        this.cantEmpleados = cantEmpleados;
        this.direccion = direccion;
        this.esCentral = esCentral;
    }
}

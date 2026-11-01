
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
    private String codigo_entidad;
    @Column(name = "numero_empleados")
    private Integer numero_empleados;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "es_central")
    private boolean es_central;

    public Sucursal() {
    }

    public Sucursal(Integer codigoSucursal, String codigo_entidad, Integer numero_empleados, String direccion, boolean es_central) {
        this.id = codigoSucursal;
        this.codigo_entidad = codigo_entidad;
        this.numero_empleados = numero_empleados;
        this.direccion = direccion;
        this.es_central = es_central;
    }
}

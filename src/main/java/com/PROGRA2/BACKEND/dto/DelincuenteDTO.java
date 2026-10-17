
package com.PROGRA2.BACKEND.dto;

import lombok.Data;

@Data
public class DelincuenteDTO {
    private Integer id;
    private Integer codigoDNI;
    private String nombre;
    private String apellido;
    private String direccion;
    private String rolUsuario;
}
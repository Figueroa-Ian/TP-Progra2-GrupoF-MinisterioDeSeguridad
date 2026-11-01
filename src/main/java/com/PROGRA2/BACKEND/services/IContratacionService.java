
package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Contratacion;
import java.util.List;

public interface IContratacionService {
    List<Contratacion> traerTodasLasContrataciones();
    Contratacion traerContratacionPorId(Integer id);
    Contratacion guardarContratacion(Contratacion contratacion);
    void eliminarContratacion(Integer id);
    Contratacion modificarContratacion(Integer id, Contratacion contratacion);
}
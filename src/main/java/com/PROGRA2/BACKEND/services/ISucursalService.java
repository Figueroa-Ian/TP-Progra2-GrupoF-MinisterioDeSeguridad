
package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Sucursal;
import java.util.List;

public interface ISucursalService {
    List<Sucursal> traerTodasLasSucursales();
    Sucursal traerSucursalPorId(Integer id);
    Sucursal guardarSucursal(Sucursal sucursal);
    void eliminarSucursal(Integer id);
    Sucursal modificarSucursal(Integer id, Sucursal sucursal);
}

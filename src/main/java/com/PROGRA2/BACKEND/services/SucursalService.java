
package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Sucursal;
import com.PROGRA2.BACKEND.repository.SucursalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SucursalService implements ISucursalService {
    
    @Autowired
    private SucursalRepository sucursalRepository;

    @Override
    public List<Sucursal> traerTodasLasSucursales() {
        return sucursalRepository.findAll();
    }

    @Override
    public Sucursal traerSucursalPorId(Integer id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sucursal no encontrado con ID: " + id));
    }

    @Override
    public Sucursal guardarSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    @Override
    public void eliminarSucursal(Integer id) {
        sucursalRepository.deleteById(id);
    }

    @Override
    public Sucursal modificarSucursal(Integer id, Sucursal sucursalModificado) {
        // 1. Buscamos el sucursal existente en la base de datos
        Sucursal sucursal = traerSucursalPorId(id);

        if (sucursal != null) {
            // 2. Actualizamos solo los atributos permitidos (ejemplo: datos personales y de sucursal)
            sucursal.setCantEmpleados(sucursalModificado.getCantEmpleados());
            
            // 3. Guardamos los cambios manteniendo el ID original
            return sucursalRepository.save(sucursal);
        }

        // Si no se encuentra, retornar null o lanzar una excepciÃ³n
        return null;
    }
}

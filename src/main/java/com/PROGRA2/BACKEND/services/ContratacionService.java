
package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Contratacion;
import com.PROGRA2.BACKEND.repository.ContratacionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratacionService implements IContratacionService {

    @Autowired
    private ContratacionRepository contratacionRepository;

    @Override
    public List<Contratacion> traerTodasLasContrataciones() {
        return contratacionRepository.findAll();
    }

    @Override
    public Contratacion traerContratacionPorId(Integer id) {
        return contratacionRepository.findById(id).orElse(null);
    }

    @Override
    public Contratacion guardarContratacion(Contratacion contratacion) {
        return contratacionRepository.save(contratacion);
    }

    @Override
    public void eliminarContratacion(Integer id) {
        contratacionRepository.deleteById(id);
    }
    
    @Override
    public Contratacion modificarContratacion(Integer id, Contratacion contratacionModificado) {
        // 1. Buscamos la contratación existente en la base de datos
        Contratacion contratacion = traerContratacionPorId(id);

        if (contratacion != null) {
            // 2. Actualizamos todos los atributos permitidos con los nuevos datos
            contratacion.setCodigoSucursal(contratacionModificado.getCodigoSucursal());
            contratacion.setCodigoVigilante(contratacionModificado.getCodigoVigilante());
            contratacion.setFecha(contratacionModificado.getFecha());
            contratacion.setConArma(contratacionModificado.isConArma());
            contratacion.setObservaciones(contratacionModificado.getObservaciones());

            // 3. Guardamos los cambios manteniendo el ID original
            return contratacionRepository.save(contratacion);
        }

        // Si no se encuentra, retornar null o lanzar una excepción
        return null;
    }
}
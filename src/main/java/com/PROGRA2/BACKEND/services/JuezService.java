package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Juez;
import com.PROGRA2.BACKEND.models.Rol;
import com.PROGRA2.BACKEND.repository.ExpedienteRepository;
import com.PROGRA2.BACKEND.repository.JuezRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JuezService implements IJuezService {

    @Autowired
    private JuezRepository juezRepository; //[cite: 1]

    @Autowired
    private ExpedienteRepository expedienteRepository;

    @Autowired
    private PersonaService personaService;

    @Override
    public List<Juez> traerTodosLosJueces() {
        return juezRepository.findAll();
    }

    @Override
    public Juez traerJuezPorId(int id) {
        return juezRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un juez con id " + id));
    }

    @Override
    @Transactional
    public void eliminarJuez(int id) {
        Juez existente = juezRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe un juez con id " + id));

        // No se elimina si tiene expedientes a su cargo (quedaría una clave huérfana)
        String clave = existente.getClaveJuzgado();
        if (clave != null) {
            long enUso = expedienteRepository.countByClaveJuez(clave);
            if (enUso > 0) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "No se puede eliminar el juez: tiene " + enUso + " expediente(s) a su cargo.");
            }
        }

        try {
            juezRepository.delete(existente);
            juezRepository.flush();   // fuerza el DELETE acá para detectar otros registros que dependan de él
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el juez: tiene registros asociados.");
        }
    }

    @Override
    public Juez guardarJuez(Juez juez) {
        personaService.validarPersonaNueva(juez);   // mayor de 18 años y DNI no repetido
        // Si querés asegurar el rol antes de guardar:
        juez.setRol(Rol.JUEZ);
        return juezRepository.save(juez);
    }

    @Override
    public Juez modificarJuez(Integer id, Juez juezModificado) {
        // 1. Buscamos el juez existente en la base de datos
        Juez juez = traerJuezPorId(id);

        if (juez != null) {
            // 2. Actualizamos solo los atributos permitidos
            juez.setClaveJuzgado(juezModificado.getClaveJuzgado());

            // 3. Guardamos los cambios manteniendo el ID original
            return juezRepository.save(juez);
        }

        // Si no se encuentra, retornar null o lanzar una excepción
        return null;
    }

}

package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Ilicito;
import com.PROGRA2.BACKEND.repository.IlicitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IlicitoService {

    @Autowired
    private IlicitoRepository ilicitoRepository;

    // Listar todos los ilicitos
    public List<Ilicito> listarIlicitos() {
        return ilicitoRepository.findAll();
    }

    // Buscar ilicito por ID
    public Optional<Ilicito> obtenerIlicitoPorId(Integer id) {
        return ilicitoRepository.findById(id);
    }

    // Guardar o actualizar un ilicito (aquí se puede aprovechar para calcular condenas si lo deseas)
    public Ilicito guardarIlicito(Ilicito ilicito) {
        return ilicitoRepository.save(ilicito);
    }

    // Eliminar un ilicito
    public void eliminarIlicito(Integer id) {
        ilicitoRepository.deleteById(id);
    }
}
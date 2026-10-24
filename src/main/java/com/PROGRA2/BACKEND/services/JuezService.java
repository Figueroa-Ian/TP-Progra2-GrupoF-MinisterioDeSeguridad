package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Juez;
import com.PROGRA2.BACKEND.models.Rol;
import com.PROGRA2.BACKEND.repository.JuezRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JuezService implements IJuezService {

    @Autowired
    private JuezRepository juezRepository; //[cite: 1]

    @Override
    public List<Juez> traerTodosLosJueces() {
        return juezRepository.findAll();
    }

    @Override
    public Juez traerJuezPorId(int id) {
        return juezRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Juez no encontrado con ID: " + id));
    }

    @Override
    public void eliminarJuez(int id) {
        juezRepository.deleteById(id);
    }

    @Override
    public Juez guardarJuez(Juez juez) {
        // Si querés asegurar el rol antes de guardar:
        juez.setRol(Rol.JUEZ);
        return juezRepository.save(juez);
    }
}

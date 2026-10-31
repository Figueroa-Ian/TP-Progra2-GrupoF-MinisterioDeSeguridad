
package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Juez;
import java.util.List;

public interface IJuezService {
    List<Juez> traerTodosLosJueces();
    Juez traerJuezPorId(int id);
    Juez guardarJuez(Juez juez);
    void eliminarJuez(int id);
    Juez modificarJuez(Integer id, Juez juez);
}
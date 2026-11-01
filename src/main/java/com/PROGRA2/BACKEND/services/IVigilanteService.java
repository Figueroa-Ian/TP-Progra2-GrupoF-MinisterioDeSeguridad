package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Vigilante;
import java.util.List;

public interface IVigilanteService {

    List<Vigilante> traerTodosLosVigilantes();

    Vigilante traerVigilantePorId(Integer id);

    Vigilante crearVigilante(String legajo, int edad, boolean disponibleTrabajar);

    Vigilante guardarVigilante(Vigilante vigilante);

    Vigilante modificarVigilante(Integer id, Vigilante detalles);

    void eliminarVigilante(Integer id);
}
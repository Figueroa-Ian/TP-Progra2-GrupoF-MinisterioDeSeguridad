package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Persona;
import java.util.List;

public interface IChusmaService {

    List<Persona> traerTodosLosChusmas();

    Persona traerChusmaPorId(Integer id);

    Persona guardarChusma(Persona investigador);

    Persona modificarChusma(Integer id, Persona detalles);

    void eliminarChusma(Integer id);
}

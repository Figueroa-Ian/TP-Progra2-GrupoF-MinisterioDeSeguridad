package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Persona;
import java.util.List;

public interface IInvestigadorService {

    List<Persona> traerTodosLosInvestigadores();

    Persona traerInvestigadorPorId(Integer id);

    Persona guardarInvestigador(Persona investigador);

    Persona modificarInvestigador(Integer id, Persona detalles);

    void eliminarInvestigador(Integer id);
}

package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Expediente;
import java.util.List;

public interface IExpedienteService {

    List<Expediente> traerTodosLosExpedientes();

    Expediente traerExpedientePorId(Integer nroExpediente);

    Expediente guardarExpediente(Expediente expediente);

    Expediente modificarExpediente(Integer nroExpediente, Expediente expedienteDetalles);

    void eliminarExpediente(Integer nroExpediente);
}
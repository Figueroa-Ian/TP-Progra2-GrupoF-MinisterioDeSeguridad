package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Ilicito;
import java.util.List;

public interface IIlicitoService {

    List<Ilicito> traerTodosLosIlicitos();

    Ilicito traerIlicitoPorId(Integer id);

    Ilicito guardarIlicito(Ilicito ilicito);

    Ilicito modificarIlicito(Integer id, Ilicito ilicitoDetalles);

    void eliminarIlicito(Integer id);
}
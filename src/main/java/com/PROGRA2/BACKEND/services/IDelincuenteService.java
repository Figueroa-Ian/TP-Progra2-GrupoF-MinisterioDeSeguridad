package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Delincuente;
import java.util.List;

public interface IDelincuenteService {
    List<Delincuente> traerTodosLosDelincuentes();
    Delincuente traerDelincuentePorId(int id);
    Delincuente guardarDelincuente(Delincuente delincuente);
    void eliminarDelincuente(int id);
    Delincuente modificarDelincuente(Integer id, Delincuente delincuente);
    List<Delincuente> verMiembrosPorIdBanda(Integer numeroBanda);
    List<Delincuente> verMiembrosPorNombreBanda(String nombreBanda);
}

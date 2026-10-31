
package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Delincuente;
import java.util.List;

public interface IDelincuenteService {
    List<Delincuente> traerTodosLosDelincuentes();
    Delincuente traerDelincuentePorId(int id);
    Delincuente guardarDelincuente(Delincuente delincuente);
    void eliminarDelincuente(int id);
    List<Delincuente> verMiembrosPorNombreBanda(String nombreBanda);
    List<Delincuente> verMiembrosPorIdBanda(Integer numeroBanda);
}


package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Delincuente;
import java.util.List;

public interface IDelincuenteService {
    List<Delincuente> traerTodosLosDelincuentes();
    Delincuente traerDelincuentePorId(int id);
    Delincuente guardarDelincuente(Delincuente delincuente);
    void eliminarDelincuente(int id);
<<<<<<< HEAD
    List<Delincuente> verMiembrosPorNombreBanda(String nombreBanda);
    List<Delincuente> verMiembrosPorIdBanda(Integer numeroBanda);
=======
    Delincuente modificarDelincuente(Integer id, Delincuente delincuente);
    List<Delincuente> verMiembrosPorIdBanda(Integer numeroBanda);
    List<Delincuente> verMiembrosPorNombreBanda(String nombreBanda);
>>>>>>> 7d4eeadfe723dd55b4e411f118fc0c6e82bcc5e9
}

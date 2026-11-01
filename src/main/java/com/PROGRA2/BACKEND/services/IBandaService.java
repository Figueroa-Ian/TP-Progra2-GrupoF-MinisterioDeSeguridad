
package com.PROGRA2.BACKEND.services;

import com.PROGRA2.BACKEND.models.Banda;
import java.util.List;

public interface IBandaService {
    List<Banda> traerTodasLosBandas();
    Banda traerBandaPorId(int id);
    Banda guardarBanda(Banda delincuente);
    void eliminarBanda(int id);
    Banda modificarBanda(Integer id, Banda delincuente);
    List<Banda> buscarBandaPorNombre(String nombre);
    List<Banda> buscarBandaPorNombreExacto(String nombre);
}

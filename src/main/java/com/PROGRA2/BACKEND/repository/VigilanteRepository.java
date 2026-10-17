
package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Vigilante;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VigilanteRepository extends JpaRepository<Vigilante, Integer>{
    Optional<Vigilante> findByLegajo(String legajo);//Optional herramienta de diseño para comunicar que un valor puede no existir de forma limpia, sin usar null ni arrojar excepciones
}

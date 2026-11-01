package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpedienteRepository extends JpaRepository<Expediente, Integer> {

    // Cuántos expedientes usan un ilícito (para no eliminar uno que está en uso)
    long countByIlicitoId(Integer idIlicito);
}
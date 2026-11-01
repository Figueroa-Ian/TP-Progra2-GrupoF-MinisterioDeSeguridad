package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpedienteRepository extends JpaRepository<Expediente, Integer> {

    // Cuántos expedientes usan un ilícito (para no eliminar uno que está en uso)
    long countByIlicitoId(Integer idIlicito);

    // Cuántos expedientes tiene a su cargo un juez (por su clave de juzgado)
    long countByClaveJuez(String claveJuez);

    // En cuántos expedientes figura un delincuente (por su clave)
    @Query("select count(e) from Expediente e where :clave member of e.clavesDelincuentes")
    long contarPorDelincuente(@Param("clave") String clave);
}
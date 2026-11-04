
package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Delito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DelitoRepository extends JpaRepository<Delito, Long> {
    // Busca si ya existe un delito con el mismo tipo (ignorando mayúsculas/minúsculas)
    boolean existsByTipoIgnoreCase(String tipo);
}
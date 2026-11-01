
package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Contratacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratacionRepository extends JpaRepository<Contratacion, Integer> {
    // Agregar métodos personalizados si los necesitamos más adelante, 
    // como buscar contrataciones por sucursal o vigilante...
}

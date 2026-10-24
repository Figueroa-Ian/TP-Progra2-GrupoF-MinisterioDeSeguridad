
package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Delincuente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelincuenteRepository extends JpaRepository<Delincuente, String> {
    
    // Spring Boot crea la consulta SQL automáticamente basándose en el nombre del método
    List<Delincuente> findByNumeroBanda(Integer numeroBanda);
    
    
}

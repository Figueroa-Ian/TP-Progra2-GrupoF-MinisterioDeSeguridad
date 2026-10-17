
package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Delincuente;
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> cd1e7a49c40530afef49ba00f62c7a291c6133a7
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
public interface DelincuenteRepository extends JpaRepository<Delincuente, Integer> {
}
=======
public interface DelincuenteRepository extends JpaRepository<Delincuente, String> {
    
    // Spring Boot crea la consulta SQL automáticamente basándose en el nombre del método
    List<Delincuente> findByNumeroBanda(Integer numeroBanda);
    
    
}
>>>>>>> cd1e7a49c40530afef49ba00f62c7a291c6133a7

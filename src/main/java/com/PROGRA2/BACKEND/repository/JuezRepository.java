
package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Juez;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuezRepository extends JpaRepository<Juez, Integer> {
    // Spring Data JPA crea automáticamente la consulta SQL por el nombre del método:
    Optional<Juez> findByClaveJuzgado(String claveJuzgado);
}

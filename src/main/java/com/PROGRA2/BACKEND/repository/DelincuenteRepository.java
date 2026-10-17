
package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Delincuente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelincuenteRepository extends JpaRepository<Delincuente, Integer> {
}

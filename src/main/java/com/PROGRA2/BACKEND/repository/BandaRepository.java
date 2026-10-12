package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Banda;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BandaRepository extends JpaRepository<Banda, Integer> {
    
    List<Banda> findByNombreBandaContainingIgnoreCase(String nombreBanda);    
    
    List<Banda> findByNombreBandaIgnoreCase(String nombreBanda);

}
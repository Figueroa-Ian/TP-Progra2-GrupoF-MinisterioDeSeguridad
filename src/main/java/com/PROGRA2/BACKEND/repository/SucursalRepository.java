package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {
    //Optional<Sucursal> findByCodigo(Integer claveSucursal);
}

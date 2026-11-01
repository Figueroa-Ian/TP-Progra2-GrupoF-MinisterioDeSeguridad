package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Ilicito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IlicitoRepository extends JpaRepository<Ilicito, Integer> {
}

package com.PROGRA2.BACKEND.repository;

import com.PROGRA2.BACKEND.models.Licores;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
Le indica a Spring que esta interfaz es un componente de persistencia. Permite 
que Spring la detecte automáticamente (component scanning), la instancie y traduzca
las excepciones de la base de datos a excepciones propias de Spring.
*/
@Repository
/*
Al extender de JpaRepository, Spring Data JPA crea automáticamente la implementación 
en tiempo de ejecución
**/
public interface LicoresRepository extends JpaRepository<Licores, Integer> {

    /*
    .save(licor) \ Ejecuta INSERT o UPDATE..
    .findAll() \ Ejecuta SELECT * FROM licor..
    .findById(id) \ Ejecuta SELECT * FROM licor WHERE id = ?..
    .deleteById(id) \ Ejecuta DELETE FROM licor WHERE id = ?..
    */
    List<Licores> findByTipo(String tipo);
    /*
    Es un Derived Query Method (Consulta derivada). Spring Data JPA lee el nombre del 
    método y genera automáticamente la consulta SQL por ti basándose en el nombre del 
    atributo tipo de tu entidad... SELECT * FROM licor WHERE tipo = 'Vodka'
    */
}
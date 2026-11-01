package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Contratacion;
import com.PROGRA2.BACKEND.services.IContratacionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/contrataciones")
public class ContratacionController {

    @Autowired
    private IContratacionService contratacionService;

    // GET http://localhost:8080/api/contrataciones
    @GetMapping
    public List<Contratacion> listarContrataciones() {
        return contratacionService.traerTodasLasContrataciones();
    }

    // GET http://localhost:8080/api/contrataciones/1
    @GetMapping("/{id}")
    public ResponseEntity<Contratacion> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(contratacionService.traerContratacionPorId(id));
    }

    // POST http://localhost:8080/api/contrataciones
    @PostMapping
    public ResponseEntity<Contratacion> crearContratacion(@RequestBody Contratacion contratacion) {
        Contratacion nuevaContratacion = contratacionService.guardarContratacion(contratacion);
        return new ResponseEntity<>(nuevaContratacion, HttpStatus.CREATED);
    }
    
    // DELETE http://localhost:8080/api/contrataciones/1   (204 si se eliminó, 404 si no existe)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarContratacion(@PathVariable("id") Integer nroContratacion) {
        contratacionService.eliminarContratacion(nroContratacion);
        return ResponseEntity.noContent().build();
    }
    
    // PUT http://localhost:8080/api/contrataciones/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Contratacion> modificarContratacion(@PathVariable Integer id, @RequestBody Contratacion contratacionDetalles) {
        Contratacion contratacionActualizada = contratacionService.modificarContratacion(id, contratacionDetalles);
        return ResponseEntity.ok(contratacionActualizada);
    }
}
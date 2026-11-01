package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Expediente;
import com.PROGRA2.BACKEND.services.IExpedienteService;
import java.util.List;
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
@RequestMapping("/api/expedientes")
public class ExpedienteController {

    private final IExpedienteService expedienteService;

    public ExpedienteController(IExpedienteService expedienteService) {
        this.expedienteService = expedienteService;
    }

    // GET http://localhost:8080/api/expedientes
    @GetMapping
    public List<Expediente> verExpedientes() {
        return expedienteService.traerTodosLosExpedientes();
    }

    // GET http://localhost:8080/api/expedientes/1   (404 si no existe)
    @GetMapping("/buscar/{id}")
    public Expediente verExpedientePorId(@PathVariable("id") Integer nroExpediente) {
        return expedienteService.traerExpedientePorId(nroExpediente);
    }

    // POST http://localhost:8080/api/expedientes
    @PostMapping
    public ResponseEntity<Expediente> crearExpediente(@RequestBody Expediente expediente) {
        Expediente nuevo = expedienteService.guardarExpediente(expediente);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // PUT http://localhost:8080/api/expedientes/1
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Expediente> modificarExpediente(@PathVariable("id") Integer nroExpediente,
                                                          @RequestBody Expediente expedienteDetalles) {
        Expediente actualizado = expedienteService.modificarExpediente(nroExpediente, expedienteDetalles);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE http://localhost:8080/api/expedientes/1   (204 si se eliminó, 404 si no existe)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarExpediente(@PathVariable("id") Integer nroExpediente) {
        expedienteService.eliminarExpediente(nroExpediente);
        return ResponseEntity.noContent().build();
    }
}
package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Ilicito;
import com.PROGRA2.BACKEND.services.IIlicitoService;
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
@RequestMapping("/api/ilicitos")
public class IlicitoController {

    private final IIlicitoService ilicitoService;

    public IlicitoController(IIlicitoService ilicitoService) {
        this.ilicitoService = ilicitoService;
    }

    // GET http://localhost:8080/api/ilicitos
    @GetMapping
    public List<Ilicito> verIlicitos() {
        return ilicitoService.traerTodosLosIlicitos();
    }

    // GET http://localhost:8080/api/ilicitos/1   (404 si no existe)
    @GetMapping("/buscar/{id}")
    public Ilicito verIlicitoPorId(@PathVariable("id") Integer id) {
        return ilicitoService.traerIlicitoPorId(id);
    }

    // POST http://localhost:8080/api/ilicitos
    @PostMapping
    public ResponseEntity<Ilicito> crearIlicito(@RequestBody Ilicito ilicito) {
        Ilicito nuevo = ilicitoService.guardarIlicito(ilicito);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    // PUT http://localhost:8080/api/ilicitos/1
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Ilicito> modificarIlicito(@PathVariable("id") Integer id,
                                                    @RequestBody Ilicito ilicitoDetalles) {
        Ilicito actualizado = ilicitoService.modificarIlicito(id, ilicitoDetalles);
        return ResponseEntity.ok(actualizado);
    }

    // DELETE http://localhost:8080/api/ilicitos/1   (204 si se eliminó, 404 si no existe, 409 si está en uso)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarIlicito(@PathVariable("id") Integer id) {
        ilicitoService.eliminarIlicito(id);
        return ResponseEntity.noContent().build();
    }
}
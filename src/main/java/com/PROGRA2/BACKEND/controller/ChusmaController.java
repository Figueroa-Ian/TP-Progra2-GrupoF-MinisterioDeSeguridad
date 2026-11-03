package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Persona;
import com.PROGRA2.BACKEND.services.IChusmaService;
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
@RequestMapping("/api/chusmas")
public class ChusmaController {

    private final IChusmaService investigadorService;

    public ChusmaController(IChusmaService investigadorService) {
        this.investigadorService = investigadorService;
    }

    // GET http://localhost:8080/api/chusmas
    @GetMapping
    public List<Persona> listarChusmas() {
        return investigadorService.traerTodosLosChusmas();
    }

    // GET http://localhost:8080/api/chusmas/1   (404 si no existe o no es investigador)
    @GetMapping("/{id}")
    public Persona buscarPorId(@PathVariable("id") Integer id) {
        return investigadorService.traerChusmaPorId(id);
    }

    // POST http://localhost:8080/api/chusmas
    @PostMapping
    public ResponseEntity<Persona> crearChusma(@RequestBody Persona investigador) {
        return new ResponseEntity<>(investigadorService.guardarChusma(investigador), HttpStatus.CREATED);
    }

    // PUT http://localhost:8080/api/chusmas/modificar/1
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Persona> modificarChusma(@PathVariable("id") Integer id,
                                                         @RequestBody Persona detalles) {
        return ResponseEntity.ok(investigadorService.modificarChusma(id, detalles));
    }

    // DELETE http://localhost:8080/api/chusmas/eliminar/1   (204, 404, o 409 si tiene usuario asociado)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarChusma(@PathVariable("id") Integer id) {
        investigadorService.eliminarChusma(id);
        return ResponseEntity.noContent().build();
    }
}

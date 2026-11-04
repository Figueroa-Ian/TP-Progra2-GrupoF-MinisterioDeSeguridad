package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Vigilante;
import com.PROGRA2.BACKEND.services.IVigilanteService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vigilantes")
public class VigilanteController {

    private final IVigilanteService vigilanteService;

    public VigilanteController(IVigilanteService vigilanteService) {
        this.vigilanteService = vigilanteService;
    }

    // GET http://localhost:8080/api/vigilantes
    @GetMapping
    public List<Vigilante> listarTodos() {
        return vigilanteService.traerTodosLosVigilantes();
    }

    // GET http://localhost:8080/api/vigilantes/1   (404 si no existe)
    @GetMapping("/{id}")
    public Vigilante buscarPorId(@PathVariable("id") Integer id) {
        return vigilanteService.traerVigilantePorId(id);
    }

    // POST http://localhost:8080/api/vigilantes   (201)
    // Body JSON: { "dni": "...", "nombre": "...", "apellido": "...", "direccion": "...",
    //              "fechaNacimiento": "1990-03-15", "rol": "VIGILANTE",
    //              "legajo": "VIG-06", "disponibleTrabajar": true }
    @PostMapping
    public ResponseEntity<Vigilante> registrarVigilante(@RequestBody Vigilante vigilante) {
        return new ResponseEntity<>(vigilanteService.guardarVigilante(vigilante), HttpStatus.CREATED);
    }

    // POST http://localhost:8080/api/vigilantes/crear?legajo=VIG-06&disponibleTrabajar=true
    @PostMapping("/crear")
    public Vigilante crearLVigilanteConParams(@RequestParam String legajo,
                                              @RequestParam boolean disponibleTrabajar) {
        return vigilanteService.crearVigilante(legajo, disponibleTrabajar);
    }

    // PUT http://localhost:8080/api/vigilantes/modificar/1
    // Body JSON: { "legajo": "VIG-01", "disponibleTrabajar": true }
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Vigilante> modificarVigilante(@PathVariable("id") Integer id,
                                                        @RequestBody Vigilante detalles) {
        return ResponseEntity.ok(vigilanteService.modificarVigilante(id, detalles));
    }

    // DELETE http://localhost:8080/api/vigilantes/eliminar/1   (204 si se eliminÃ³, 404 si no existe, 409 si tiene registros asociados)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarVigilante(@PathVariable("id") Integer id) {
        vigilanteService.eliminarVigilante(id);
        return ResponseEntity.noContent().build();
    }
}
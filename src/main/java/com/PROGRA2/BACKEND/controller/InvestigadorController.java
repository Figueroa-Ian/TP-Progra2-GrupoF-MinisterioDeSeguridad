package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Persona;
import com.PROGRA2.BACKEND.services.IInvestigadorService;
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
@RequestMapping("/api/investigadores")
public class InvestigadorController {

    private final IInvestigadorService investigadorService;

    public InvestigadorController(IInvestigadorService investigadorService) {
        this.investigadorService = investigadorService;
    }

    // GET http://localhost:8080/api/investigadores
    @GetMapping
    public List<Persona> listarInvestigadores() {
        return investigadorService.traerTodosLosInvestigadores();
    }

    // GET http://localhost:8080/api/investigadores/1   (404 si no existe o no es investigador)
    @GetMapping("/{id}")
    public Persona buscarPorId(@PathVariable("id") Integer id) {
        return investigadorService.traerInvestigadorPorId(id);
    }

    // POST http://localhost:8080/api/investigadores
    @PostMapping
    public ResponseEntity<Persona> crearInvestigador(@RequestBody Persona investigador) {
        return new ResponseEntity<>(investigadorService.guardarInvestigador(investigador), HttpStatus.CREATED);
    }

    // PUT http://localhost:8080/api/investigadores/modificar/1
    @PutMapping("/modificar/{id}")
    public ResponseEntity<Persona> modificarInvestigador(@PathVariable("id") Integer id,
                                                         @RequestBody Persona detalles) {
        return ResponseEntity.ok(investigadorService.modificarInvestigador(id, detalles));
    }

    // DELETE http://localhost:8080/api/investigadores/eliminar/1   (204, 404, o 409 si tiene usuario asociado)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarInvestigador(@PathVariable("id") Integer id) {
        investigadorService.eliminarInvestigador(id);
        return ResponseEntity.noContent().build();
    }
}

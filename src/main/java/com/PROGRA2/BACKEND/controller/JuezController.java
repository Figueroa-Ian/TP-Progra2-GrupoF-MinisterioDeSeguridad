package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Juez;
import com.PROGRA2.BACKEND.services.IJuezService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/jueces")
public class JuezController {

    //private final JuezRepository juezRepository;
    @Autowired
    private IJuezService juezService;

    // GET http://localhost:8080/api/jueces
    @GetMapping
    public List<Juez> listarJueces() {
        return juezService.traerTodosLosJueces();
    }

    // GET http://localhost:8080/api/jueces/1
    @GetMapping("/{id}")
    public ResponseEntity<Juez> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(juezService.traerJuezPorId(id));
    }
    
    // POST http://localhost:8080/api/jueces
        @PostMapping
        public ResponseEntity<Juez> crearJuez(@RequestBody Juez juez) {
        Juez nuevoJuez = juezService.guardarJuez(juez);
            return new ResponseEntity<>(nuevoJuez, HttpStatus.CREATED);
        }
}

package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Ilicito;
import com.PROGRA2.BACKEND.services.IlicitoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IlicitoController {

    @Autowired
    private IlicitoService ilicitoService;

    @GetMapping("/buscar/ilicitos")
    public List<Ilicito> obtenerTodos() {
        return ilicitoService.listarIlicitos();
    }

    @GetMapping("/buscar/ilicito/{id}")
    public ResponseEntity<Ilicito> obtenerPorId(@PathVariable Integer id) {
        return ilicitoService.obtenerIlicitoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ilicito crearIlicito(@RequestBody Ilicito ilicito) {
        return ilicitoService.guardarIlicito(ilicito);
    }

    @DeleteMapping("/eliminar/ilicito/{id}")
    public ResponseEntity<Void> eliminarIlicito(@PathVariable Integer id) {
        if (ilicitoService.obtenerIlicitoPorId(id).isPresent()) {
            ilicitoService.eliminarIlicito(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
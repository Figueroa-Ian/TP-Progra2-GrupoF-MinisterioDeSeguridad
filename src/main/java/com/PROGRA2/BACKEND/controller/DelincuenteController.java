
package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.dto.DelincuenteDTO;
import com.PROGRA2.BACKEND.service.DelincuenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delincuentes")
@RequiredArgsConstructor
public class DelincuenteController {

    private final DelincuenteService service;

    @PostMapping
    public ResponseEntity<DelincuenteDTO> crearDelincuente(@RequestBody DelincuenteDTO dto) {
        return ResponseEntity.ok(service.crearDelincuente(dto));
    }

    @GetMapping
    public ResponseEntity<List<DelincuenteDTO>> listarDelincuentes() {
        return ResponseEntity.ok(service.listarDelincuentes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DelincuenteDTO> actualizarDelincuente(@PathVariable Integer id, @RequestBody DelincuenteDTO dto) {
        return ResponseEntity.ok(service.actualizarDelincuente(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDelincuente(@PathVariable Integer id) {
        service.eliminarDelincuente(id);
        return ResponseEntity.noContent().build();
    }
}
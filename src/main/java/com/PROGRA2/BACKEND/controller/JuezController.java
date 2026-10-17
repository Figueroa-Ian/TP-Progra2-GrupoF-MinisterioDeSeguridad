
package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Juez;
import com.PROGRA2.BACKEND.repository.JuezRepository;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jueces")
public class JuezController {

    private final JuezRepository juezRepository;

    public JuezController(JuezRepository juezRepository) {
        this.juezRepository = juezRepository;
    }

    // GET http://localhost:8080/api/jueces
    @GetMapping
    public List<Juez> listarTodos() {
        return juezRepository.findAll();
    }

    // GET http://localhost:8080/api/jueces/1
    @GetMapping("/{id}")
    public ResponseEntity<Juez> buscarPorId(@PathVariable Integer id) {
        return juezRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

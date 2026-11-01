package com.PROGRA2.BACKEND.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Catálogos de solo lectura para los desplegables del front.
 *   GET /api/catalogos/tipos-juridicos
 *   GET /api/catalogos/tipos-accion
 *   GET /api/catalogos/tipos-intencion
 * Devuelve [{ "id": 1, "nombre": "...", "descripcion": "..." }, ...]
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/catalogos")
public class CatalogoController {

    // Lista cerrada: el nombre de tabla NUNCA sale de lo que manda el cliente
    private static final Map<String, String> TABLAS = Map.of(
            "tipos-juridicos", "tipo_juridico",
            "tipos-accion", "tipo_accion",
            "tipos-intencion", "tipo_intencion");

    private final JdbcTemplate jdbc;

    public CatalogoController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/{nombre}")
    public List<Map<String, Object>> verCatalogo(@PathVariable("nombre") String nombre) {
        String tabla = TABLAS.get(nombre);
        if (tabla == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el catálogo " + nombre);
        }
        return jdbc.queryForList("SELECT id, nombre, descripcion FROM " + tabla + " ORDER BY id");
    }
}
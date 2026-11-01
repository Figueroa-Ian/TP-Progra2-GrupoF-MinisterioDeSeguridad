package com.PROGRA2.BACKEND.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * Devuelve siempre el motivo de los errores de validación (400, 404, 409...)
 * como JSON {status, message}, para que el front se lo muestre al usuario.
 */
@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> manejarEstado(ResponseStatusException ex) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("status", ex.getStatusCode().value());
        cuerpo.put("message", ex.getReason() != null
                ? ex.getReason()
                : "Error " + ex.getStatusCode().value());
        return ResponseEntity.status(ex.getStatusCode()).body(cuerpo);
    }
}

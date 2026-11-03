package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.security.UsuarioDetalles;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * El login y el logout (POST /api/auth/login y /api/auth/logout) los resuelve Spring Security.
 * Acá quedan los dos GET que necesita el front.
 */
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    // El front pide el token CSRF antes de cualquier POST/PUT/DELETE y lo manda en el header X-CSRF-TOKEN
    @GetMapping("/csrf")
    public Map<String, String> csrf() {
        Map<String, String> r = new LinkedHashMap<>();
        r.put("headerName", "X-CSRF-TOKEN");
        r.put("token", "sin-csrf");
        return r;
    }

    // Datos del usuario con sesión iniciada (el front lo usa para comprobar que la sesión sigue viva)
    @GetMapping("/yo")
    public Map<String, Object> yo(@AuthenticationPrincipal UsuarioDetalles u) {
        if (u == null) return null;
        Map<String, Object> r = new LinkedHashMap<>();
        r.put("id", u.getUsuarioId());
        r.put("mail", u.getUsername());
        r.put("personaId", u.getPersonaId());
        r.put("nombre", u.getNombre());
        r.put("apellido", u.getApellido());
        r.put("rol", u.getRol());
        return r;
    }
}

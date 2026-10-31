package com.PROGRA2.BACKEND.controller;

import com.PROGRA2.BACKEND.models.Persona;
import com.PROGRA2.BACKEND.models.Usuario;
import com.PROGRA2.BACKEND.repository.UsuarioRepository;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // el Front corre en otro puerto (Payara/GlassFish)
public class LoginController {

    private final UsuarioRepository usuarioRepository;

    public LoginController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Body: { "mail": "...", "contrasenia": "..." }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> datos) {
        String mail = datos.get("mail");
        String contrasenia = datos.get("contrasenia");

        if (mail == null || contrasenia == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Usuario> encontrado = usuarioRepository.findByMail(mail.trim());

        // Las contraseñas están en texto plano en la BDD del TP (ej: 'prog123')
        if (encontrado.isEmpty() || !encontrado.get().getContrasenia().equals(contrasenia)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuario u = encontrado.get();
        Persona p = u.getPersona();

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("id", u.getId());
        respuesta.put("mail", u.getMail());
        respuesta.put("personaId", p.getId());
        respuesta.put("nombre", p.getNombre());
        respuesta.put("apellido", p.getApellido());
        respuesta.put("rol", p.getRol().name());
        return ResponseEntity.ok(respuesta);
    }
}

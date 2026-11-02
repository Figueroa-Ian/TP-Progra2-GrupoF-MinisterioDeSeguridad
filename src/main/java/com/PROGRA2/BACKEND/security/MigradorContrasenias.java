package com.PROGRA2.BACKEND.security;

import com.PROGRA2.BACKEND.models.Usuario;
import com.PROGRA2.BACKEND.repository.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Al arrancar, reemplaza las contraseñas que todavía están en texto plano por su hash BCrypt.
 * Es idempotente: las que ya son hash no se tocan. Los usuarios siguen entrando con la misma contraseña.
 */
@Component
public class MigradorContrasenias implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public MigradorContrasenias(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        int migradas = 0;
        for (Usuario u : usuarioRepository.findAll()) {
            String actual = u.getContrasenia();
            if (actual != null && !esBcrypt(actual)) {
                u.setContrasenia(encoder.encode(actual));
                usuarioRepository.save(u);
                migradas++;
            }
        }
        if (migradas > 0) {
            System.out.println("[Seguridad] Contraseñas pasadas de texto plano a BCrypt: " + migradas);
        }
    }

    private boolean esBcrypt(String s) {
        return s.startsWith("$2a$") || s.startsWith("$2b$") || s.startsWith("$2y$");
    }
}

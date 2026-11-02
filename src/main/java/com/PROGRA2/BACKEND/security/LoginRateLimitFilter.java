package com.PROGRA2.BACKEND.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Corta los intentos de login ANTES de verificar la contraseña si el usuario o la IP están bloqueados.
 * No lleva @Component a propósito: lo agrega SecurityConfig dentro de la cadena de seguridad.
 */
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private final LoginAttemptService intentos;
    private final String urlLogin;

    public LoginRateLimitFilter(LoginAttemptService intentos, String urlLogin) {
        this.intentos = intentos;
        this.urlLogin = urlLogin;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        boolean esLogin = "POST".equalsIgnoreCase(req.getMethod())
                && req.getRequestURI().equals(req.getContextPath() + urlLogin);
        if (esLogin) {
            long espera = intentos.segundosDeBloqueo(req.getParameter("mail"), req.getRemoteAddr());
            if (espera > 0) {
                long minutos = (espera + 59) / 60;
                Map<String, Object> cuerpo = new LinkedHashMap<>();
                cuerpo.put("status", 429);
                cuerpo.put("codigo", "BLOQUEADO");
                cuerpo.put("message", "Demasiados intentos fallidos. Probá de nuevo en " + minutos
                        + (minutos == 1 ? " minuto." : " minutos."));
                res.setHeader("Retry-After", String.valueOf(espera));
                RespuestaJson.escribir(res, 429, cuerpo);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}

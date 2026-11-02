package com.PROGRA2.BACKEND.security;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/** Escucha los eventos de Spring Security y alimenta el control de intentos fallidos. */
@Component
public class LoginAttemptListener {

    private final LoginAttemptService intentos;

    public LoginAttemptListener(LoginAttemptService intentos) {
        this.intentos = intentos;
    }

    // Se dispara tanto con contraseña mala como con usuario inexistente (Spring lo convierte en BadCredentials)
    @EventListener
    public void alFallar(AuthenticationFailureBadCredentialsEvent evento) {
        intentos.registrarFallo(evento.getAuthentication().getName(), ipActual());
    }

    @EventListener
    public void alIngresar(AuthenticationSuccessEvent evento) {
        intentos.registrarExito(evento.getAuthentication().getName(), ipActual());
    }

    // Solo getRemoteAddr(): X-Forwarded-For lo puede falsificar cualquiera
    private String ipActual() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes servlet) {
            return servlet.getRequest().getRemoteAddr();
        }
        return "desconocida";
    }
}

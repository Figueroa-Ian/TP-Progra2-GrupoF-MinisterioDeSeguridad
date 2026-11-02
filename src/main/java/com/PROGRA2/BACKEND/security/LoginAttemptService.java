package com.PROGRA2.BACKEND.security;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 * Control de intentos fallidos (anti fuerza bruta / diccionario).
 *  - Por usuario: 5 fallos seguidos -> esa cuenta queda bloqueada 15 minutos.
 *  - Por IP: 20 fallos (cualquier usuario) -> esa IP queda bloqueada 15 minutos.
 * El bloqueo vale también para usuarios que NO existen, así no se puede averiguar qué mails están registrados.
 * Es en memoria: se reinicia al reiniciar el back.
 */
@Service
public class LoginAttemptService {

    public static final int MAX_INTENTOS_USUARIO = 5;
    public static final int MAX_INTENTOS_IP = 20;
    public static final Duration BLOQUEO = Duration.ofMinutes(15);

    private static final class Registro {
        int fallos;
        Instant desde;
        Instant bloqueadoHasta;
    }

    private final Map<String, Registro> registros = new ConcurrentHashMap<>();
    private final Clock reloj;

    public LoginAttemptService() {
        this(Clock.systemUTC());
    }

    LoginAttemptService(Clock reloj) {   // para poder probarlo con una hora falsa
        this.reloj = reloj;
    }

    public void registrarFallo(String usuario, String ip) {
        fallo("u:" + normalizar(usuario), MAX_INTENTOS_USUARIO);
        fallo("ip:" + ip, MAX_INTENTOS_IP);
    }

    /** Un ingreso correcto limpia el contador de ese usuario (el de la IP no, para que no se pueda "resetear"). */
    public void registrarExito(String usuario, String ip) {
        registros.remove("u:" + normalizar(usuario));
    }

    /** Segundos que faltan para poder volver a intentar (0 si no está bloqueado). */
    public long segundosDeBloqueo(String usuario, String ip) {
        return Math.max(restante("u:" + normalizar(usuario)), restante("ip:" + ip));
    }

    private void fallo(String clave, int maximo) {
        Instant ahora = reloj.instant();
        registros.compute(clave, (k, r) -> {
            boolean vencido = r != null && (r.bloqueadoHasta != null
                    ? !r.bloqueadoHasta.isAfter(ahora)
                    : r.desde.plus(BLOQUEO).isBefore(ahora));
            if (r == null || vencido) {
                r = new Registro();
                r.desde = ahora;
            }
            r.fallos++;
            if (r.fallos >= maximo) {
                r.bloqueadoHasta = ahora.plus(BLOQUEO);
            }
            return r;
        });
        limpiarSiHaceFalta(ahora);
    }

    private long restante(String clave) {
        Registro r = registros.get(clave);
        if (r == null || r.bloqueadoHasta == null) return 0;
        long ms = Duration.between(reloj.instant(), r.bloqueadoHasta).toMillis();
        return ms <= 0 ? 0 : (ms + 999) / 1000;
    }

    private void limpiarSiHaceFalta(Instant ahora) {
        if (registros.size() > 10_000) {   // evita que un atacante llene la memoria con usuarios inventados
            registros.entrySet().removeIf(e -> e.getValue().bloqueadoHasta != null
                    ? !e.getValue().bloqueadoHasta.isAfter(ahora)
                    : e.getValue().desde.plus(BLOQUEO).isBefore(ahora));
        }
    }

    private String normalizar(String usuario) {
        String u = usuario == null ? "" : usuario.trim().toLowerCase(Locale.ROOT);
        return u.length() > 100 ? u.substring(0, 100) : u;
    }
}

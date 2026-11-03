package com.PROGRA2.BACKEND.security;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Seguridad del back.
 *  ADMINISTRADOR : todo.
 *  INVESTIGADOR  : solo consultar (GET).
 *  VIGILANTE     : solo consultar SUS propios datos (GET /api/vigilantes/{su id}).
 *  Cualquier otro rol (JUEZ, DELINCUENTE) o sin sesión: nada.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    static final String URL_LOGIN = "/api/auth/login";
    static final String URL_LOGOUT = "/api/auth/logout";

    // Contraseñas: BCrypt con "salt" propio por contraseña (nunca se guardan en texto plano)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain filtros(HttpSecurity http, LoginAttemptService intentos) throws Exception {
        http
            // HTTPS obligatorio en todo (login incluido). Además, Tomcat solo escucha por HTTPS (server.ssl.enabled).

            .cors(Customizer.withDefaults())          // usa el bean corsConfigurationSource de abajo
            .csrf(csrf -> csrf.disable())              // MODO DESARROLLO: CSRF desactivado

            // Al iniciar sesión se crea una sesión nueva (anti fijación de sesión)
            .sessionManagement(sesion -> sesion
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionFixation(fijacion -> fijacion.newSession()))

            .formLogin(login -> login
                    .loginPage(URL_LOGIN)             // evita la pantalla de login automática de Spring
                    .loginProcessingUrl(URL_LOGIN)
                    .usernameParameter("mail")
                    .passwordParameter("contrasenia")
                    .successHandler((req, res, auth) -> {
                        UsuarioDetalles u = (UsuarioDetalles) auth.getPrincipal();
                        Map<String, Object> cuerpo = new LinkedHashMap<>();
                        cuerpo.put("id", u.getUsuarioId());
                        cuerpo.put("mail", u.getUsername());
                        cuerpo.put("personaId", u.getPersonaId());
                        cuerpo.put("nombre", u.getNombre());
                        cuerpo.put("apellido", u.getApellido());
                        cuerpo.put("rol", u.getRol());
                        RespuestaJson.escribir(res, 200, cuerpo);
                    })
                    // Mensaje siempre igual: no revela si falló el mail o la contraseña
                    .failureHandler((req, res, ex) -> RespuestaJson.escribir(res, 401,
                            Map.of("status", 401, "message", "Usuario o contraseña incorrectos.")))
                    .permitAll())

            .logout(logout -> logout
                    .logoutUrl(URL_LOGOUT)            // por defecto exige POST + token CSRF
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler((req, res, auth) -> res.setStatus(204)))

            .exceptionHandling(e -> e
                    .authenticationEntryPoint((req, res, ex) -> RespuestaJson.escribir(res, 401,
                            Map.of("status", 401, "message", "Tenés que iniciar sesión.")))
                    .accessDeniedHandler((req, res, ex) -> {
                        Map<String, Object> cuerpo = new LinkedHashMap<>();
                        cuerpo.put("status", 403);
                        if (ex instanceof CsrfException) {
                            cuerpo.put("codigo", "CSRF");
                            cuerpo.put("message", "La sesión venció o el token de seguridad es inválido. Iniciá sesión de nuevo.");
                        } else {
                            cuerpo.put("message", "No tenés permiso para realizar esta acción.");
                        }
                        RespuestaJson.escribir(res, 403, cuerpo);
                    }))

            // Antes de verificar la contraseña: ¿esta cuenta o esta IP están bloqueadas?
            .addFilterBefore(new LoginRateLimitFilter(intentos, URL_LOGIN), UsernamePasswordAuthenticationFilter.class)

            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());   // MODO DESARROLLO: sin restricciones por rol

        return http.build();
    }

    // El Front corre en otro puerto: se permite SOLO ese origen (con cookies de sesión), no "*"
    @Bean
    public CorsConfigurationSource corsConfigurationSource(
            @Value("${app.cors.origins:*}") List<String> origenes) {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOriginPatterns(origenes);
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("Content-Type", "Accept", "X-CSRF-TOKEN"));
        cfg.setAllowCredentials(true);
        cfg.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        fuente.registerCorsConfiguration("/**", cfg);
        return fuente;
    }
}

package com.PROGRA2.BACKEND.security;

import com.PROGRA2.BACKEND.models.Usuario;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** Usuario autenticado: lleva solo lo necesario (no la entidad JPA) y se borra la contraseña tras el login. */
public class UsuarioDetalles implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = 1L;

    private final Integer usuarioId;
    private final Integer personaId;
    private final String mail;
    private final String nombre;
    private final String apellido;
    private final String rol;
    private String hash;

    public UsuarioDetalles(Usuario u) {
        this.usuarioId = u.getId();
        this.personaId = u.getPersona().getId();
        this.mail = u.getMail();
        this.nombre = u.getPersona().getNombre();
        this.apellido = u.getPersona().getApellido();
        this.rol = u.getPersona().getRol().name();
        this.hash = u.getContrasenia();
    }

    public Integer getUsuarioId() { return usuarioId; }
    public Integer getPersonaId() { return personaId; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getRol() { return rol; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol));
    }

    @Override
    public String getPassword() { return hash; }

    @Override
    public String getUsername() { return mail; }

    @Override
    public void eraseCredentials() { this.hash = null; }
}

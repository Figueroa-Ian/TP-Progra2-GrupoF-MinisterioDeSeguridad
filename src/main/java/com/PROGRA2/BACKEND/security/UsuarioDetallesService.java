package com.PROGRA2.BACKEND.security;

import com.PROGRA2.BACKEND.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Carga el usuario por mail con Spring Data (consulta parametrizada, sin SQL armado a mano).
 * Si no existe lanza UsernameNotFoundException, que Spring Security convierte en un BadCredentialsException
 * genérico: por eso nunca se puede saber si falló el mail o la contraseña.
 */
@Service
public class UsuarioDetallesService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetallesService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        String limpio = mail == null ? "" : mail.trim();
        return usuarioRepository.findByMail(limpio)
                .map(UsuarioDetalles::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario inexistente"));
    }
}

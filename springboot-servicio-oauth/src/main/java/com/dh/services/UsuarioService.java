package com.dh.services;

import com.dh.clients.UsuarioFeignClient;
import com.dh.models.entity.Usuario;
import feign.FeignException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements IUsuarioService, UserDetailsService {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsuarioFeignClient client;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            Usuario usuario = client.findByUsername(username);

            List<GrantedAuthority> authorities = usuario.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                    .peek(authority -> log.info("Role: " + authority.getAuthority()))
                    .collect(Collectors.toList());
            log.info("Usuario autenticado: " + username);

            return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true,
                    true, true, authorities);

        } catch (FeignException e) {
            log.info("Usuario no encontrado: " + username);
            throw new UsernameNotFoundException("Usuario: '" + username + "' no existe");
        }
    }


    @Override
    public Usuario findByUsername(String username) {
        return client.findByUsername(username);
    }

    @Override
    public Usuario update(Usuario usuario, Long id) {
        return client.update(usuario, id);
    }
}

package com.dh.security.event;

import brave.Tracer;
import com.dh.models.entity.Usuario;
import com.dh.services.IUsuarioService;
import feign.FeignException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    private Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private Tracer tracer;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {

        if (authentication.getName().equalsIgnoreCase("frontendapp")) {
            return;
        }

        // TODO: este no funciona, revisar por que:
//        if (authentication.getPrincipal() instanceof WebAuthenticationDetails) {
//            return;
//        }

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String mensaje = "User " + user.getUsername() + " logged in successfully";
        System.out.println(mensaje);
        log.info(mensaje);

        Usuario usuario = usuarioService.findByUsername(authentication.getName());

        if (usuario.getIntentos() != null && usuario.getIntentos() > 0) {
            usuario.setIntentos(0);
            usuarioService.update(usuario, usuario.getId());
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException e, Authentication authentication) {
        String mensaje = "User " + authentication.getName() + " failed to login";
        System.out.println(mensaje);
        log.error(mensaje);

        try {

            StringBuilder errors = new StringBuilder();
            errors.append(mensaje);

            Usuario usuario = usuarioService.findByUsername(authentication.getName());
            if (usuario.getIntentos() == null) {
                usuario.setIntentos(0);
            }

            log.info("Intentos actual es de: " + usuario.getIntentos());
            usuario.setIntentos(usuario.getIntentos() + 1);
            log.info("Intentos despues es de: " + usuario.getIntentos());

            errors.append(" - " + "Intentos del login: " + usuario.getIntentos());

            if (usuario.getIntentos() >= 3) {
                log.error(String.format("El usuario %s des-habilitado por maximo intentos",
                        authentication.getName()));
                errors.append(" - " + "El usuario " + authentication.getName() + " des-habilitado" +
                        " por maximo intentos");
                usuario.setEnabled(false);
            }

            usuarioService.update(usuario, usuario.getId());

            tracer.currentSpan().tag("error.mensaje", errors.toString());

        } catch (FeignException ex) {
            log.error(String.format("Error al buscar el usuario %s", authentication.getName()));
        }
    }
}

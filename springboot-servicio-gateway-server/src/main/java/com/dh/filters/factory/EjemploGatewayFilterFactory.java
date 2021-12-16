package com.dh.filters.factory;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class EjemploGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploGatewayFilterFactory.Configuracion> {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    public EjemploGatewayFilterFactory() {
        super(Configuracion.class);
    }

    @Override
    public GatewayFilter apply(Configuracion config) {
        return new OrderedGatewayFilter((exchange, chain) -> {
            logger.info("Ejecutando pre gateway filter factory: " + config.mensaje);


            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {

                        Optional.ofNullable(config.cookieValor).ifPresent(cookie -> {
                                exchange.getResponse().addCookie(ResponseCookie.from(config.cookieNombre, cookie).build());
                                });

                        logger.info("Ejecutando post gateway filter " +
                                "factory: " + config.mensaje);
                    }));
        }, 2);
    }

    @Override
    public String name() {
        return "EjemploCookie";
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("mensaje", "cookieNombre", "cookieValor");
    }

    @Data
    public static class Configuracion {
        private String mensaje;
        private String cookieValor;
        private String cookieNombre;
    }

}

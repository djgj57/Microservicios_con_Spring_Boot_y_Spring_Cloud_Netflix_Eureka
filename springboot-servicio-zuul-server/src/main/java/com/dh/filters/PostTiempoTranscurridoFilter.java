package com.dh.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {

    private static Logger log = org.slf4j.LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("Entrando a post filter");

        Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
        Long tiempoFinal = System.currentTimeMillis();
        Long tiempoTranscurrido = tiempoFinal - tiempoInicio;

        log.info(String.format("tiempo transcurrido en segundos %s",
                tiempoTranscurrido.doubleValue()/1000));
        log.info(String.format("tiempo transcurrido en mileseg %s",
                tiempoTranscurrido));


        return null;
    }
}

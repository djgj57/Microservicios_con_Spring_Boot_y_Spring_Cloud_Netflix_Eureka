package com.dh.models.service;

import com.dh.models.Item;
import com.dh.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements IItemService{

    @Autowired
    private RestTemplate clientRest;

    public List<Item> findAll() {
        List<Producto> productos = Arrays.asList(clientRest.getForObject("http://servicio-productos/listar",
                Producto[].class));

        return productos.stream().map(producto -> new Item(producto, 1)).collect(Collectors.toList());
    }

    public Item findById(Long id, Integer cantidad) {
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", id.toString());
        Producto producto = clientRest.getForObject("http://servicio-productos/ver/{id}",
                Producto.class, pathVariables);
        return new Item(producto, cantidad);
    }

    @Override
    public Producto save(Producto producto) {
        HttpEntity<Producto> body = new HttpEntity<>(producto);
        ResponseEntity<Producto> response = clientRest.exchange("http://servicio-productos/crear",
                HttpMethod.POST, body, Producto.class);
        Producto productoResponse = response.getBody();
        return productoResponse;
    }

    @Override
    public Producto update(Producto producto, Long id) {
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", id.toString());
        HttpEntity<Producto> body = new HttpEntity<>(producto);
        ResponseEntity<Producto> response = clientRest.exchange("http://servicio-productos/editar" +
                "/{id}", HttpMethod.PUT, body, Producto.class, pathVariables);
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        Map<String, String> pathVariables = new HashMap<String, String>();
        pathVariables.put("id", id.toString());
        clientRest.delete("http://servicio-productos/eliminar/{id}", pathVariables);
    }
}

package com.dh.controllers;

import com.dh.models.Item;
import com.dh.models.Producto;
import com.dh.models.service.IItemService;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    @Qualifier("serviceFeign")
//    @Qualifier("serviceRestTemplate")
    private IItemService itemService;

    @GetMapping("/listar")
    public List<Item> listar(@RequestParam(name="nombre", required = false) String nombre, @RequestHeader(name=
            "token-request", required = false) String token){
        System.out.println("Nombre: " + nombre);
        System.out.println("Token: " + token);
        return itemService.findAll();
    }

//
//    @HystrixCommand(fallbackMethod = "metodoAlternativo")
//    @GetMapping("/ver/{id}/cantidad/{cantidad}")
//    public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad){
//        return itemService.findById(id, cantidad);
//    }

    public Item metodoAlternativo(Long id, Integer cantidad){
        Item item = new Item();
        Producto producto = new Producto();
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Camara Sony");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return item;
    }

}

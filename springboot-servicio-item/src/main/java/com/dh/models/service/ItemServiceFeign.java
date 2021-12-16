package com.dh.models.service;

import com.dh.clientes.IProductoClienteRest;
import com.dh.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("serviceFeign")
//@Primary - utilizamos esto si no queremos ponerle nombres y que sea siempre la principal a
// implementar
public class ItemServiceFeign implements IItemService {

    @Autowired
    private IProductoClienteRest clienteFeign;

    @Override
    public List<Item> findAll() {
        return clienteFeign.listar().stream().map(producto -> new Item(producto, 1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        return new Item( clienteFeign.detalle(id), cantidad);
    }
}

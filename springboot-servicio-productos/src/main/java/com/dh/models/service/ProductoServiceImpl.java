package com.dh.models.service;

import com.dh.models.dao.ProductoDao;
import com.dh.models.entity.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class ProductoServiceImpl implements IProductoService {

    // Aca con final, puedo utilizar @RequiredArgsConstructor
//    private final ProductoDao productoDao;

    // Aca devo utilizar Autowired
    @Autowired
    private ProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return (List<Producto>) productoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoDao.findById(id).orElse(null);
    }
}

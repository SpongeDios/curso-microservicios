package com.formacionbdi.springboot.app.productos.services.impl;


import com.formacionbdi.springboot.app.commons.models.Producto;
import com.formacionbdi.springboot.app.productos.repositories.ProductoDao;
import com.formacionbdi.springboot.app.productos.services.ProductoService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoDao productoDao;

    public ProductoServiceImpl(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    @Override
    public List<Producto> findAll() {
        List<Producto> productos = productoDao.findAll();
        return productos;
    }

    @Override
    public Producto findById(Long id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    public void deleteById(Long id) {
        productoDao.deleteById(id);
    }
}

package com.formacionbdi.springboot.app.productos.services.impl;

import com.formacionbdi.springboot.app.productos.models.Producto;
import com.formacionbdi.springboot.app.productos.repositories.ProductoDao;
import com.formacionbdi.springboot.app.productos.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;


    @Override
    public List<Producto> findAll() {
        List<Producto> productos = productoDao.findAll();
        return productos;
    }

    @Override
    public Producto findById(Long id) {
        return productoDao.findById(id).orElse(null);
    }
}

package com.formacionbdi.springboot.app.productos.services;

import com.formacionbdi.springboot.app.productos.models.Producto;

import java.util.List;

public interface ProductoService {
    public List<Producto> findAll();
    public Producto findById(Long id);
}

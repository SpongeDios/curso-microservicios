package com.formacionbdi.springboot.app.productos.services;

import com.formacionbdi.springboot.app.productos.models.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
}

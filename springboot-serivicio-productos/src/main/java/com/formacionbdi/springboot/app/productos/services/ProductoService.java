package com.formacionbdi.springboot.app.productos.services;

import com.formacionbdi.springboot.app.commons.models.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save (Producto producto);
    void deleteById(Long id);
}

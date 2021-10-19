package com.formacionbdi.springboot.app.productos.repositories;

import com.formacionbdi.springboot.app.productos.models.Producto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoDao extends CrudRepository<Producto, Long> {
    List<Producto> findAll();
}

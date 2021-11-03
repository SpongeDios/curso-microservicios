package com.formacionbdi.springboot.app.items.services;

import com.formacionbdi.springboot.app.items.models.Item;
import com.formacionbdi.springboot.app.items.models.Producto;

import java.util.List;

public interface ItemService {

    List<Item> findAll();
    Item findById(Long id, Integer cantidad);
    Producto save(Producto producto);
    Producto update(Producto producto, Long id);
    void delete(Long id);
}

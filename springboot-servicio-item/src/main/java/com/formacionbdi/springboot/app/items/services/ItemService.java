package com.formacionbdi.springboot.app.items.services;

import com.formacionbdi.springboot.app.items.models.Item;

import java.util.List;

public interface ItemService {

    List<Item> findAll();
    Item findById(Long id, Integer cantidad);

}

package com.formacionbdi.springboot.app.items.controllers;

import com.formacionbdi.springboot.app.items.models.Item;
import com.formacionbdi.springboot.app.items.models.Producto;
import com.formacionbdi.springboot.app.items.services.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items/listar")
    public List<Item> allProducts(){
        return itemService.findAll();
    }

    @GetMapping("/items/ver/{id}/cantidad/{cantidad}")
    public Item itemById(@PathVariable("id") Long id, @PathVariable("cantidad") Integer cantidad){
        return itemService.findById(id, cantidad);
    }

}

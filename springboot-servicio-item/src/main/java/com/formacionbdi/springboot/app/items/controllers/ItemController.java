package com.formacionbdi.springboot.app.items.controllers;

import com.formacionbdi.springboot.app.items.models.Item;
import com.formacionbdi.springboot.app.items.models.Producto;
import com.formacionbdi.springboot.app.items.services.ItemService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
//@RequestMapping("/api/items")
public class ItemController {
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private ItemService itemService;
    private CircuitBreakerFactory circuitBreakerFactory;

    public ItemController(ItemService itemService, CircuitBreakerFactory circuitBreakerFactory) {
        this.itemService = itemService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @GetMapping("/listar")
    public List<Item> allProducts(){
        return itemService.findAll();
    }

    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item itemById(@PathVariable Long id, @PathVariable Integer cantidad){
//        return itemService.findById(id, cantidad);
        return circuitBreakerFactory.create("items").run(() -> itemService.findById(id, cantidad/*, e -> metodoAlternativo(id, cantidad, e) SIN METODO ALTERNATIVO= ERROR COMO RESPUESTA*/ ));
    }

    public Item metodoAlternativo(Long id, Integer cantidad, Throwable e){
        logger.info(e.getMessage());
        Item item = new Item();
        Producto producto = new Producto();
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Metodo alternativo");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return item;
    }

}

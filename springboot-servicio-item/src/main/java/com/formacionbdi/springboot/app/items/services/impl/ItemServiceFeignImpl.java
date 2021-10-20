package com.formacionbdi.springboot.app.items.services.impl;

import com.formacionbdi.springboot.app.items.clientes.ProductoClienteRest;
import com.formacionbdi.springboot.app.items.models.Item;
import com.formacionbdi.springboot.app.items.models.Producto;
import com.formacionbdi.springboot.app.items.services.ItemService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary //se usa primary, porque tenemos dos clases que implementan la interface ItemService.
//Al tener primary, este tendra prioridad sobre el otro.
public class ItemServiceFeignImpl implements ItemService {

    //Se puede inyectar porque es un cliente feign, y con esta anotacion entra al contenedor de spring
    private ProductoClienteRest productoClienteRest;

    public ItemServiceFeignImpl(ProductoClienteRest productoClienteRest) {
        this.productoClienteRest = productoClienteRest;
    }

    @Override
    public List<Item> findAll() {
        List<Item> listado = productoClienteRest.listar().stream().map(producto -> new Item(producto, 1)).collect(Collectors.toList());
        return listado;
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Producto producto = productoClienteRest.findById(id);
        return new Item(producto, cantidad);
    }
}

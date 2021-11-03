package com.formacionbdi.springboot.app.items.services.impl;

import com.formacionbdi.springboot.app.items.models.Item;
import com.formacionbdi.springboot.app.items.models.Producto;
import com.formacionbdi.springboot.app.items.services.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Primary
public class ItemServiceImpl implements ItemService {

    @Value("${microservicio.url.allProducts}")
    private String listar;

    @Value("${microservicio.url.productById}")
    private String verPorId;

    private RestTemplate restTemplate;

    public ItemServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Item> findAll() {
        List<Producto> productos = Arrays.asList(restTemplate.getForObject("http://servicio-productos/listar", Producto[].class));
        return productos.stream().map(producto -> new Item(producto,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer cantidad) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        Producto producto = restTemplate.getForObject("http://servicio-productos/ver/{id}", Producto.class, pathVariables);
        return new Item(producto, cantidad);
    }

    @Override
    public Producto save(Producto producto) {
        HttpEntity<Producto> entity = new HttpEntity<Producto>(producto);
        return restTemplate.postForObject("http://servicio-productos/crear", entity, Producto.class);
    }

    @Override
    public Producto update(Producto producto, Long id) {
        HttpEntity<Producto> entity = new HttpEntity<Producto>(producto);
        ResponseEntity<Producto> response = restTemplate.exchange("http://servicio-productos/editar/"+id, HttpMethod.PUT, entity, Producto.class);
        return response.getBody();
    }

    @Override
    public void delete(Long id) {
        restTemplate.delete("http://servicio-productos/eliminar/"+id);
    }
}

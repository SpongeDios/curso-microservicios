package com.formacionbdi.springboot.app.productos.controllers;

import com.formacionbdi.springboot.app.productos.models.Producto;
import com.formacionbdi.springboot.app.productos.services.ProductoService;
import com.formacionbdi.springboot.app.productos.services.impl.ProductoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductoController {

    private ServletWebServerApplicationContext webServerApplicationContext;
    private ProductoService productoService;

    public ProductoController(ServletWebServerApplicationContext webServerApplicationContext, ProductoService productoService) {
        this.webServerApplicationContext = webServerApplicationContext;
        this.productoService = productoService;
    }

    @Value("${server.port}")
    private Integer port;


    @GetMapping("/listar")
    public List<Producto> listar(){
        return productoService.findAll().stream().map(producto -> {
            producto.setPort(webServerApplicationContext.getWebServer().getPort());
            return producto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/ver/{id}")
    public Producto producto(@PathVariable("id") Long idProducto) throws InterruptedException {

        if (idProducto.equals(10L)) {
            throw new IllegalStateException("Producto no encontrado");
        }

        if (idProducto.equals(7L)) {
            TimeUnit.SECONDS.sleep(5L);
        }
        Producto producto = productoService.findById(idProducto);
        producto.setPort(webServerApplicationContext.getWebServer().getPort());
        return producto;
    }

    @PostMapping("/crear")
    //@ResponseStatus(HttpStatus.CREATED) //<--- Util pero flaite
    public ResponseEntity<Producto> crear(@RequestBody Producto producto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Producto> update(@PathVariable Long id, @RequestBody Producto productoBody){
        Producto producto = productoService.findById(id);
        producto.setNombre(productoBody.getNombre());
        producto.setPrecio(productoBody.getPrecio());
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.save(producto));
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Long id){
        productoService.deleteById(id);
        Map<String, String> mapa = new HashMap<>();
        mapa.put("message", "Producto borrado satisfactoriamente!");
    }
}

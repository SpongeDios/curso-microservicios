package com.formacionbdi.springboot.app.productos.controllers;

import com.formacionbdi.springboot.app.productos.models.Producto;
import com.formacionbdi.springboot.app.productos.services.ProductoService;
import com.formacionbdi.springboot.app.productos.services.impl.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductoController {

    private ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/listar")
    public List<Producto> listar(){
        return productoService.findAll();
    }

    @GetMapping("/ver/{id}")
    public Producto producto(@PathVariable("id") Long idProducto){
        return productoService.findById(idProducto);
    }



}

package com.formacionbdi.springboot.app.items.clientes;

import com.formacionbdi.springboot.app.items.models.Item;
import com.formacionbdi.springboot.app.items.models.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "servicio-productos", url = "localhost:8001")  //Con esta anotacion se declara que es un cliente feign
//En el parametro name, le pasamos el nombre del microservicio al que queremos mandar la peticion
//En el parametro se le pasa la URL de nuestro microservicio a consumir
public interface ProductoClienteRest {
    //Aca se anotan los metodos que necesitamos, tal cual lo hicimos con restTemplate
    //Los getMapping viajan hacia nuestro microservicio de productos
    @GetMapping("/listar")
    List<Producto> listar();

    @GetMapping("/ver/{id}")
    Producto findById(@PathVariable Long id);

}

package com.formacionbdi.springboot.app.items.controllers;

import com.formacionbdi.springboot.app.items.models.Item;
import com.formacionbdi.springboot.app.items.models.Producto;
import com.formacionbdi.springboot.app.items.services.ItemService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

 // @RefreshScope--> Permite actualizar los componentes/variables de entorno que nos traemos del configserver
// sin necesidad de reiniciar el microservicio o el configserver
@RestController
@RefreshScope
//@RequestMapping("/api/items")
public class ItemController {
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private ItemService itemService;
    private CircuitBreakerFactory circuitBreakerFactory;
    private Environment environment;


    public ItemController(ItemService itemService, CircuitBreakerFactory circuitBreakerFactory, Environment environment) {
        this.itemService = itemService;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.environment = environment;
    }

    @Value("${configuracion.text}")
    private String texto;

    @Value("${server.port}")
    private String port;

    @GetMapping("/listar")
    public List<Item> allProducts(){
        return itemService.findAll();
    }

    @GetMapping("/ver/{id}/cantidad/{cantidad}")
    public Item itemById(@PathVariable Long id, @PathVariable Integer cantidad){
//        return itemService.findById(id, cantidad);
        return circuitBreakerFactory.create("items").run(() -> itemService.findById(id, cantidad/*, e -> metodoAlternativo(id, cantidad, e) SIN METODO ALTERNATIVO= ERROR COMO RESPUESTA*/ ));
    }

    //Circuit breaker con anotaciones -> Con esta anotacion toma los valores del YML
    @CircuitBreaker(name="items", fallbackMethod = "metodoAlternativo" /*<- metodo alternativo es opcional*/)
    @GetMapping("/ver2/{id}/cantidad/{cantidad}")
    public Item itemById2(@PathVariable Long id, @PathVariable Integer cantidad){
        return  itemService.findById(id, cantidad);
    }

    //Con este metodo, configurando los timeout a 2 segundos, y provocando mas del 50% de timeout
    //NO pasa al estado abierto, solo sigue normalmente
    @TimeLimiter(name="items" , fallbackMethod = "metodoAlternativo2"/*<- metodo alternativo es opcional y tiene q se compatible con CompletableFuture*/)
//    Si combinamos la anotacion time limiter junto con circuit breaker, sucedera un comportamiento parecido al de arriba
    @GetMapping("/ver3/{id}/cantidad/{cantidad}")
    public CompletableFuture<Item> itemById3(@PathVariable Long id, @PathVariable Integer cantidad){
        return  CompletableFuture.supplyAsync(() -> itemService.findById(id, cantidad));
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

    public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e){
        logger.info(e.getMessage());
        Item item = new Item();
        Producto producto = new Producto();
        item.setCantidad(cantidad);
        producto.setId(id);
        producto.setNombre("Metodo alternativo");
        producto.setPrecio(500.00);
        item.setProducto(producto);
        return CompletableFuture.supplyAsync(() -> item);
    }

    @GetMapping("/obtener-config")
    public ResponseEntity<?> obtenerConfig(){
        logger.info("CALLING API /obtener-config");
        Map<String, String> mapa = new HashMap<>();
        mapa.put("texto", texto);
        mapa.put("puerto", port);

        String activeAmbient = String.valueOf(environment.getProperty("spring.cloud.config.profile"));
        if (activeAmbient.equals("dev")){
            mapa.put("autor.nombre", environment.getProperty("configuracion.autor.nombre"));
            mapa.put("autor.email", environment.getProperty("configuracion.autor.email"));
        }


        return ResponseEntity.status(HttpStatus.OK).body(mapa);
    }

    @PostMapping("/crear")
    public ResponseEntity crear(@RequestBody Producto producto){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.save(producto));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity editar (@PathVariable Long id, @RequestBody Producto producto){
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.update(producto, id));
    }

    @DeleteMapping("/eliminar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id){
        itemService.delete(id);
    }

}

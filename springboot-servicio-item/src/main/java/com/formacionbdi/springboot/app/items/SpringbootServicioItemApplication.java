package com.formacionbdi.springboot.app.items;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@EnableFeignClients //Permite habilitar los clientes feign y tambien permite inyectar los clientes.
@SpringBootApplication
public class SpringbootServicioItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootServicioItemApplication.class, args);
    }

}

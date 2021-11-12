package com.formacionbdi.springboot.app.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.formaciobdi.springboot.app.usuarios.commons.models"})
public class SpringbootServicioUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootServicioUsuariosApplication.class, args);
    }

}

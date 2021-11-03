package com.formacionbdi.springboot.app.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class SpringBootConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootConfigServerApplication.class, args);
    }

}

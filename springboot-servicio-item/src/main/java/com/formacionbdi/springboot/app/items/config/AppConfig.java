package com.formacionbdi.springboot.app.items.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.ConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    Bean que se inyectaa para customizar las variables por defecto de resilience4j
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer(){
        return factory -> factory.configureDefault(id -> {
            return new Resilience4JConfigBuilder(id)
                    .circuitBreakerConfig(CircuitBreakerConfig.custom()
                            .slidingWindowSize(10) //cantidad de peticiones a realizar, pasó de 100 a 10
                            .failureRateThreshold(50) //% de umbral de fallas permitidas
                            .waitDurationInOpenState(Duration.ofSeconds(10L)) //10 segundos dentro del estado semi-abierto
                            .permittedNumberOfCallsInHalfOpenState(5) //numero de peticiones en estado semi-abierto
                            .slowCallRateThreshold(50) // % de umbral de llamadas lentas si pasa del 50,pasa a abierto
                            .slowCallDurationThreshold(Duration.ofSeconds(2L))//tiempo que se deberia demorar para ser considerado una llamada lenta
                            .build())
                    .timeLimiterConfig(TimeLimiterConfig.custom()
                            .timeoutDuration(Duration.ofSeconds(6L)) //--> tiempo considerado timeout. Le pongo 6 porq en el controller de productos
                            .build())                                // --> tiene un timeout de 5 sec, si le pongo 5 o menos salta la excepcion timeout
                    .build();
        });
    }


}

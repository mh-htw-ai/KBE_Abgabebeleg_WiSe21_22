package com.eevilcorp.gateway_microservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GatewayAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayAPIApplication.class, args);
    }

    /*@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("r1", r -> r.host("**.baeldung.com")
                        .and()
                        .path("/baeldung")
                        .uri("http://baeldung.com"))
                .route(r -> r.host("**.baeldung.com")
                        .and()
                        .path("/myOtherRouting")
                        .filters(f -> f.prefixPath("/myPrefix"))
                        .uri("http://othersite.com"))
                .build();
    }*/

}
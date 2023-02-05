package com.soyphea.springcloudgatewayexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringCloudGatewayExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayExampleApplication.class, args);
    }
//
//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/backend/**")
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri("http://localhost:8089"))
//                .build();
//    }

}

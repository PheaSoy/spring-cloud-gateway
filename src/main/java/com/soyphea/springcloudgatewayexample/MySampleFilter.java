package com.soyphea.springcloudgatewayexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.rmi.ServerException;
import java.util.Map;
import java.util.UUID;

//@Component
public class MySampleFilter implements GlobalFilter {


    private String serviceConfigBaseUrl;

    WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:9999/backend")
                .build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return getWebClient().get()
                .uri("/users/check/"+exchange.getRequest().getHeaders().getFirst("test"))
                .retrieve()
                .onStatus(status -> !status.is2xxSuccessful(), response ->
                        response.bodyToMono(String.class)
                                .onErrorMap(error -> {
                                    throw new RuntimeException("Book error map");
                                })
                                .flatMap(error -> {
                                    throw new RuntimeException("Boom!");
                                })
                )
                .bodyToMono(String.class)
                .flatMap(response -> {
                    exchange.getRequest()
                            .mutate()
                            .header("adding_key", UUID.randomUUID().toString())
                            .build();
                    return chain.filter(exchange);
                });
    }

}

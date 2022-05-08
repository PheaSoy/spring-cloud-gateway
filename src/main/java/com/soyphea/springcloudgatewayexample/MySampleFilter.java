package com.soyphea.springcloudgatewayexample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class MySampleFilter implements GlobalFilter {


    private String serviceConfigBaseUrl;

    WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8089/backend")
                .build();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return getWebClient().get()
                .uri("/names/hello_world")
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    exchange.getRequest().mutate()
                            .header("adding_key", response.get("key").toString())
                            .build();
                    return chain.filter(exchange);
                });
    }

}

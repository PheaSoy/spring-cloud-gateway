package com.soyphea.springcloudgatewayexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class MyGlobalFilter implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger(MyGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Global filter is executing.");
        exchange.getRequest()
                .mutate()
                .header("start_time", System.currentTimeMillis() + "")
                .header("code", UUID.randomUUID().toString())
                .build();
        return chain.filter(exchange);
    }

}

package com.soyphea.springcloudgatewayexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class ExampleWebFilter implements WebFilter {
    static private Logger LOGGER = LoggerFactory.getLogger(ExampleWebFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
                             WebFilterChain webFilterChain) {
        LOGGER.info("Request:{}", serverWebExchange.getRequest().getPath());
        LOGGER.info("Filter is executing within the internal controller.");
        if (serverWebExchange.getRequest().getPath().value().startsWith("/internal/")) {
            LOGGER.info("Internal controller filter");
        } else return webFilterChain.filter(serverWebExchange);
        serverWebExchange.getResponse()
                .getHeaders().add("web-filter", "web-filter-test");
        return webFilterChain.filter(serverWebExchange);
    }
}
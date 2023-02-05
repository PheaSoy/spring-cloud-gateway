package com.soyphea.springcloudgatewayexample;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
@AllArgsConstructor
public class PostLoggingGlobalFiltersConfigurations {

//    @Autowired
//    private Tracer tracer;

    private final DefaultHttpLoggerFormatter defaultHttpLoggerFormatter;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        defaultHttpLoggerFormatter.log(exchange);
                    }));
        };
    }
}
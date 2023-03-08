package com.soyphea.springcloudgatewayexample;

import com.soyphea.springcloudgatewayexample.output.LogOutputer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@Slf4j
@AllArgsConstructor

public class PostLoggingGlobalFiltersConfigurations {

    @Autowired
    private List<LogOutputer> logOutputers;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        if (logOutputers.size() > 0)
                            logOutputers
                                    .stream()
                                    .forEach(ouputer -> ouputer.ouput(APILogBuilder.buildAPILog(exchange)));
                        else
                            LOGGER.debug("No output of logs for executing.");
                    }));
        };
    }
}
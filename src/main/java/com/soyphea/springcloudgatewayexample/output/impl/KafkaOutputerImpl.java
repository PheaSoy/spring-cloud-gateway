package com.soyphea.springcloudgatewayexample.output.impl;

import com.soyphea.springcloudgatewayexample.APILog;
import com.soyphea.springcloudgatewayexample.output.LogOutputer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@ConditionalOnProperty(value = "logging.output.kafka.enabled", havingValue = "true")
@Component
@Slf4j
public class KafkaOutputerImpl implements LogOutputer {

    @Autowired
    private StreamBridge streamBridge;

    KafkaOutputerImpl() {
        log.info("{} output is instantiated", this.getClass().getSimpleName());
    }

    @Override
    public void ouput(APILog apiLog) {
        log.info("Send to kafka for log.");
        streamBridge.send("producer-out-0",
                apiLog);
    }

    @Bean
    public Consumer<APILog> consumer() {
        return message -> log.info("received apilogs" + message);
    }
}

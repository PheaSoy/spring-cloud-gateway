package com.soyphea.springcloudgatewayexample.output.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soyphea.springcloudgatewayexample.APILog;
import com.soyphea.springcloudgatewayexample.output.LogOutputer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConsoleLogOutputerImpl implements LogOutputer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Override
    public void ouput(APILog apiLog) {
        try {
            LOGGER.info(objectMapper.writeValueAsString(apiLog));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

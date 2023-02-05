package com.soyphea.springcloudgatewayexample;
import brave.Tracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class DefaultHttpLoggerFormatter  {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public void log(ServerWebExchange exchange) {

        String userId ="12345";
        String channel =
                exchange.getRequest().getHeaders().getFirst("channel");
        String deviceId = "device_id";
        String reqIP = getClientRequestIP(exchange);
        String startTime = exchange.getRequest()
                .getHeaders()
                .getFirst("start_time");
        String correctionId = exchange.getRequest()
                .getHeaders()
                .getFirst("correctionId");
        long executeTime = 0;
        if (StringUtils.hasLength(startTime)) {
            long startTimeMs = Long.parseLong(startTime);
            long endTime = System.currentTimeMillis();
            executeTime = endTime - startTimeMs;
        }
        org.springframework.cloud.sleuth.Span span = exchange.getAttribute(Span.class.getName());
        var value = exchange.getResponse().getStatusCode().value();
        var code = exchange.getRequest().getHeaders().getFirst("code");
        APILog apiRequestJson = APILog.builder()
                .status(value)
                .duration(executeTime + ".ms")
                .clientId("1234")
                .deviceId("deviceId")
                .service("service")
                .timestamp(System.currentTimeMillis() + "")
                .correctionId(correctionId)
                .requestIp(reqIP)
                .userId(userId)
                .requestUri(exchange.getRequest().getPath().value())
                .requestMethod(exchange.getRequest().getMethod().name())
                .channel(channel)
                .apiCode(code)
                .traceId(span.context().traceId())
                .build();
        try {
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            log.info(objectMapper.writeValueAsString(apiRequestJson));
        } catch (Exception exception) {
            log.error("Logging json object mapper error.", exception);
        }
    }

    private static String getClientRequestIP(ServerWebExchange exchange) {
        String reqIP = exchange.getRequest().getHeaders()
                .getFirst("X-Original-Forwarded-For");
        if (StringUtils.hasLength(reqIP))
            return reqIP;
        else return "";
    }

}

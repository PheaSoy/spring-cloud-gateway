package com.soyphea.springcloudgatewayexample;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.sleuth.Span;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class APILogBuilder {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static APILog buildAPILog(ServerWebExchange exchange) {

        var headers = exchange.getRequest()
                .getHeaders();
        String userId = headers.getFirst("cif");
        String clientId = headers.getFirst("client_id");
        String channel = headers.getFirst("channel");
        String deviceId = headers.getFirst("device_id");
        String reqIP = IPUtils.getClientRequestIP(exchange);
        String startTime = headers.getFirst("start_time");
        String correctionId = headers.getFirst("correctionId");
        long executeTime = 0;
        if (StringUtils.hasLength(startTime)) {
            long startTimeMs = Long.parseLong(startTime);
            long endTime = System.currentTimeMillis();
            executeTime = endTime - startTimeMs;
        }
        org.springframework.cloud.sleuth.Span span = exchange.getAttribute(Span.class.getName());
        Route route = exchange.getAttribute(org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        var value = exchange.getResponse().getStatusCode().value();
        var code = exchange.getRequest().getHeaders().getFirst("code");
        return APILog.builder()
                .responseCode(value)
                .duration(executeTime + ".ms")
                .clientId(clientId)
                .deviceId(deviceId)
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
                .routeId(route.getId())
                .apiCode(code)
                .build();

    }


}

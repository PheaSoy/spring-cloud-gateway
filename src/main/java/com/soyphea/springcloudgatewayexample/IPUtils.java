package com.soyphea.springcloudgatewayexample;

import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

public class IPUtils {

    public static String getClientRequestIP(ServerWebExchange exchange) {
        String reqIP = exchange.getRequest().getHeaders()
                .getFirst("X-Original-Forwarded-For");
        if (StringUtils.hasLength(reqIP))
            return reqIP;
        else return "";
    }
}

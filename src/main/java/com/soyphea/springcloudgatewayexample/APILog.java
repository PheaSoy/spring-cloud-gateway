package com.soyphea.springcloudgatewayexample;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class APILog {

    private int responseCode;
    private String requestIp;
    private String userId;
    private String channel;
    private String timestamp;
    private String duration;
    private String clientId;
    private String service;
    private String deviceId;
    private String requestMethod;
    private String requestUri;
    private String correctionId;
    private String apiCode;
    private String traceId;
    private String routeId;
    private String routeUi;

}

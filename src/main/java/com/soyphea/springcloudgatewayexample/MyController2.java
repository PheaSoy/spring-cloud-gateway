package com.soyphea.springcloudgatewayexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/internal")
public class MyController2 {

    @GetMapping("/hello_world2")
    Mono<String> greeting() {
        return Mono.just("Hello World!");
    }
}

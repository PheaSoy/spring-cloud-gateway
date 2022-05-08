package com.soyphea.springcloudgatewayexample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class MyController {

    @GetMapping("/hello_world")
    Mono<String> greeting() {
        return Mono.just("Hello World!");
    }
}

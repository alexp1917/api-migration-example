package org.example.migration.api.apples.middle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(path = "/api")
public class ApiController {
    @RequestMapping(method = RequestMethod.GET, path = "/{service}/**")
    public Mono<ResponseEntity<String>> get(@PathVariable String service) {
        return Mono.just(ResponseEntity.ok(service));
    }
}

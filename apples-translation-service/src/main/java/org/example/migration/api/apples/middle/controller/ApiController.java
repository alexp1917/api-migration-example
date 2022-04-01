package org.example.migration.api.apples.middle.controller;

import org.example.migration.api.apples.middle.service.ApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(path = "/api")
public class ApiController extends BaseController {
    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @RequestMapping(path = "/{service}/**")
    public Mono<? extends ResponseEntity<?>> get(ServerHttpRequest serverHttpRequest, @PathVariable String service) {
        return Mono.fromCallable(() -> apiService.process(service, serverHttpRequest)).subscribeOn(scheduler);
    }
}

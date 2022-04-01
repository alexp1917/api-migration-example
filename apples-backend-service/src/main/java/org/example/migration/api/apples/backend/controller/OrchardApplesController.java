package org.example.migration.api.apples.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.migration.api.apples.backend.service.ApplesService;
import org.example.migration.common.backend.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping(path = "/services/orchard")
public class OrchardApplesController extends BaseController {

    private static final String TOKEN_FIELD = "token";
    private static final int DEFAULT_NUMBER = 10;
    private final ApplesService applesService;

    public OrchardApplesController(ApplesService applesService) {
        this.applesService = applesService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/apples")
    public Mono<ResponseEntity<Result>> apples(@RequestParam String token,
                                               @RequestParam(name = "count", required = false) Integer count) {
        int countValue = Objects.requireNonNullElse(count, DEFAULT_NUMBER);
        log.debug("got a request for {} apples, actually using {} (default is {})", count, countValue, DEFAULT_NUMBER);
        return Mono.fromCallable(() -> applesService.getAllApples(countValue)).map(this::ok);
    }

}

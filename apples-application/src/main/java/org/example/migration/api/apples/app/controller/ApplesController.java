package org.example.migration.api.apples.app.controller;

import org.example.migration.api.apples.app.entity.Apple;
import org.example.migration.api.apples.app.service.ApplesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

/**
 * Top most layer - this is, e.g., what the frontend will interact with
 */
@Controller
@RequestMapping(path = "/apples")
public class ApplesController extends BaseController {
    private final ApplesService applesService;

    public ApplesController(ApplesService applesService) {
        this.applesService = applesService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Mono<ResponseEntity<Apple>> get(@PathVariable("id") UUID id) {
        return Mono.fromCallable(() -> applesService.getOne(id)).subscribeOn(scheduler).map(this::ok);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Mono<ResponseEntity<List<Apple>>> getAll() {
        return Mono.fromCallable(applesService::getAllApples).subscribeOn(scheduler).map(this::ok);
    }
}

package org.example.migration.api.apples.middle.controller;

import org.springframework.http.ResponseEntity;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public abstract class BaseController {
    private static final int THREADS = 500;
    private static final int QUEUE = 100;
    protected static Scheduler scheduler = Schedulers.newBoundedElastic(THREADS, QUEUE, "controller");

    public <T> ResponseEntity<T> ok(T item) {
        return ResponseEntity.ok(item);
    }
}

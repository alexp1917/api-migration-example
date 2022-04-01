package org.example.migration.api.apples.middle.service;

import org.springframework.http.server.reactive.ServerHttpRequest;

public interface ProxyServiceParams {
    ProxyServiceParams parse(ServerHttpRequest request);
}

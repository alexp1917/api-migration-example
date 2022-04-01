package org.example.migration.api.apples.app.service;

import org.example.migration.api.apples.app.entity.Apple;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * This service will query the backend by talking to the translator service
 */
@Service
public class ApplesService {
    public Apple getOne(UUID id) {
        return new Apple().setId(id).setName("example").setColor("red");
    }

    public List<Apple> getAllApples() {
        return List.of(getOne(UUID.randomUUID()));
    }
}

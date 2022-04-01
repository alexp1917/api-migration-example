package org.example.migration.api.apples.backend.service;


import org.example.migration.common.backend.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This service will query its own internal backend (e.g. database)
 */
@Service
public class ApplesService {
    public Result getOne(UUID id) {
        Result result = new Result();
        result.setCount(1);
        result.setEntity(new ArrayList<>());
        result.getEntity().add(new Result.Property().setKey("id").setValue(id.toString()));
        result.getEntity().add(new Result.Property().setKey("name").setValue("example"));
        result.getEntity().add(new Result.Property().setKey("color").setValue("red"));

        return result;
    }

    public Result getAllApples(int count) {
        Result result = new Result();
        result.setEntity(new ArrayList<>());

        for (int i = 0; i < count; i++) {
            Result one = getOne(UUID.randomUUID());
            result.getEntity().addAll(one.getEntity());
        }

        result.setCount(result.getEntity().size());
        return result;
    }
}

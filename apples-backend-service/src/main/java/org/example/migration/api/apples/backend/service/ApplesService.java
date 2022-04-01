package org.example.migration.api.apples.backend.service;


import org.example.migration.api.apples.backend.model.EntityBuilder;
import org.example.migration.common.backend.Result;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * This service will query its own internal backend (e.g. database)
 */
@Service
public class ApplesService {
    public Result getOne(UUID id) {
        Result result = new Result();
        result.setCount(1);
        EntityBuilder entity = new EntityBuilder()
                .add(new Result.Property().setKey("id").setValue(id.toString()))
                .add(new Result.Property().setKey("name").setValue("example"))
                .add(new Result.Property().setKey("color").setValue("red"));
        result.getEntity().add(entity.build());

        return result;
    }

    public Result getAllApples(int count) {
        Result result = new Result();

        for (int i = 0; i < count; i++) {
            Result one = getOne(UUID.randomUUID());
            result.getEntity().addAll(one.getEntity());
        }

        result.setCount(result.getEntity().size());
        return result;
    }
}

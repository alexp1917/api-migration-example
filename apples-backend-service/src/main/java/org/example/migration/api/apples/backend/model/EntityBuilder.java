package org.example.migration.api.apples.backend.model;

import org.example.migration.common.backend.Result;

import java.util.ArrayList;

/**
 * Since entities are represented as lists, this make it easier
 */
public class EntityBuilder {
    private final ArrayList<Result.Property> properties = new ArrayList<>();

    public EntityBuilder add(Result.Property property) {
        properties.add(property);
        return this;
    }

    public ArrayList<Result.Property> build() {
        return properties;
    }
}

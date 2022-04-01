package org.example.migration.api.apples.app.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * The internal data model for use by the application - not for talking to other backend applications
 */
@Accessors(chain = true)
@Data
public class Apple {
    private UUID id;
    private String name;
    private String color;
}

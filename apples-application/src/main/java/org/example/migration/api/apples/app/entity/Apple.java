package org.example.migration.api.apples.app.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Accessors(chain = true)
@Data
public class Apple {
    private UUID id;
    private String name;
    private String color;
}

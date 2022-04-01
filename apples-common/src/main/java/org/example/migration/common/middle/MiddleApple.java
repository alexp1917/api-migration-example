package org.example.migration.common.middle;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Data
public class MiddleApple {
    private String name;
    private String color;
}

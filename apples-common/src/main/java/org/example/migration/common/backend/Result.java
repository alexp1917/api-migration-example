package org.example.migration.common.backend;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class Result {
    private Integer count;
    private List<Property> entity;

    @Accessors(chain = true)
    @Data
    public static class Property {
        private String key;
        private String value;
    }
}

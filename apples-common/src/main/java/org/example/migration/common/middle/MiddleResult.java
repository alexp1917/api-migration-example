package org.example.migration.common.middle;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Data
public class MiddleResult<T> {
    private Integer count;
    private List<T> entities;

    public List<T> getEntities() {
        if (entities == null) entities = new ArrayList<>();

        return entities;
    }
}

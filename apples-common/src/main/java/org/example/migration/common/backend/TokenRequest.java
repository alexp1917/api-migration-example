package org.example.migration.common.backend;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TokenRequest {
    private String id;
    private String secret;
}

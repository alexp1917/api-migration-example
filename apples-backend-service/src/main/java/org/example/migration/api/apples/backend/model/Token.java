package org.example.migration.api.apples.backend.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Token {
    private String token;
    private Date issued;
    private Date expiresAt;
}

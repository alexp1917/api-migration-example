package org.example.migration.api.apples.middle.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchServiceException extends RuntimeException {
    public NoSuchServiceException(String message) {
        super(message);
    }
}

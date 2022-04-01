package org.example.migration.api.apples.middle.service;

import org.example.migration.api.apples.middle.property.BackendProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public abstract class BaseProxyService<P extends ProxyServiceParams> implements ProxyService<P> {
    protected BackendProperties backendProperties;

    @Autowired
    public void setBackendProperties(BackendProperties backendProperties) {
        this.backendProperties = backendProperties;
    }

    protected <T> ResponseEntity<T> handleBadValue(ResponseEntity<T> entity) {
        if (entity == null || !entity.getStatusCode().is2xxSuccessful() || entity.getBody() == null) {
            HttpStatus statusCode = entity == null ? HttpStatus.NOT_FOUND : entity.getStatusCode();
            throw new ResponseStatusException(statusCode);
        }
        return entity;
    }

}

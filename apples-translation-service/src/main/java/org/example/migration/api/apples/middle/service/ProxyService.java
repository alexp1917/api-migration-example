package org.example.migration.api.apples.middle.service;

import org.springframework.http.ResponseEntity;

public interface ProxyService<Params extends ProxyServiceParams> {
    Params newParams();

    AvailableProxyService whichService();

    ResponseEntity<?> serviceRequest(Params params);
}

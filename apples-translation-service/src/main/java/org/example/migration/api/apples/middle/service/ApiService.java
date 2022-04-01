package org.example.migration.api.apples.middle.service;

import lombok.extern.slf4j.Slf4j;
import org.example.migration.api.apples.middle.model.exception.NoSuchServiceException;
import org.example.migration.api.apples.middle.model.exception.ParamParsingException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApiService {
    private final Map<AvailableProxyService, ProxyService<?>> map;

    public ApiService(List<ProxyService<?>> services) {
        map = services.stream().collect(Collectors.toMap(ProxyService::whichService, Function.identity()));
    }

    public ResponseEntity<?> process(String service, ServerHttpRequest serverHttpRequest) {
        // first make sure that the service is one of the known enum types
        AvailableProxyService availableService = getServiceFromEnum(service);

        // then make sure it is implemented (present in the map)
        ProxyService<?> proxyService = getServiceFromMap(availableService);

        // then actually perform the request
        return proxyService.serviceRequest(getServiceParams(serverHttpRequest, proxyService));
    }

    /**
     * Less than ideal implementation, provided this way for proof of concept.
     * <p>
     * It creates an instance of the parameter class for the respective proxy service,
     * then parses it from the downstream request.
     */
    @SuppressWarnings("unchecked")
    private <T extends ProxyServiceParams> T getServiceParams(ServerHttpRequest serverHttpRequest, ProxyService<?> proxyService) {
        ProxyServiceParams o = proxyService.newParams();

        try {
            return (T) o.parse(serverHttpRequest);
        } catch (Throwable e) {
            throw new ParamParsingException("Could not create params for " + proxyService.whichService() + " service", e);
        }
    }

    private ProxyService<?> getServiceFromMap(AvailableProxyService availableService) {
        ProxyService<?> proxyService = map.get(availableService);
        if (proxyService == null) {
            throw new NoSuchServiceException("There is no service found for name: " + availableService);
        }
        return proxyService;
    }

    private AvailableProxyService getServiceFromEnum(String service) {
        try {
            return AvailableProxyService.valueOf(service);
        } catch (IllegalArgumentException exception) {
            throw new NoSuchServiceException("There is no available service called: " + service);
        }
    }
}

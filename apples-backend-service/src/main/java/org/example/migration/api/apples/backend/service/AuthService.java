package org.example.migration.api.apples.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.example.migration.api.apples.backend.property.AuthProperties;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    private final AuthProperties authProperties;

    public AuthService(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    /**
     * @param id         like username, access key, client id
     * @param credential like password, secret key, client secret
     * @return whether the combination is good or not
     */
    public boolean good(String id, String credential) {
        boolean b = authProperties.getDefaultUser().getId().equals(id) && authProperties.getDefaultUser().getSecret().equals(credential);
        log.debug("Authentication {} for {}", b ? "successful" : "failed", id);
        return b;
    }
}

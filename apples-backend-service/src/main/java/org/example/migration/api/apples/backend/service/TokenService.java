package org.example.migration.api.apples.backend.service;

import org.example.migration.api.apples.backend.model.Token;
import org.example.migration.api.apples.backend.model.exception.TokenException;
import org.example.migration.api.apples.backend.property.AuthProperties;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {
    private final AuthService authService;
    private final AuthProperties authProperties;

    public TokenService(AuthService authService, AuthProperties authProperties) {
        this.authService = authService;
        this.authProperties = authProperties;
    }

    public Token issue(String serviceName, String username, String password) {
        if (authService.good(username, password)) {
            String token = serviceName == null ? "success!" : "success! you can access " + serviceName;
            Instant now = Instant.now();
            Date issued = Date.from(now);
            Date expiresAt = Date.from(now.plusSeconds(authProperties.getTokenTimeout()));
            return new Token().setToken(token).setIssued(issued).setExpiresAt(expiresAt);
        }

        throw new TokenException("Can't grant token to " + username);
    }
}

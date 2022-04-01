package org.example.migration.api.apples.backend.controller;

import org.example.migration.api.apples.backend.model.Token;
import org.example.migration.api.apples.backend.service.TokenService;
import org.example.migration.common.backend.TokenRequest;
import org.example.migration.common.backend.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

import java.time.temporal.ChronoField;

@Controller
@RequestMapping(path = "/services")
public class ServicesController extends BaseController {

    private final TokenService tokenService;

    public ServicesController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/token")
    public Mono<ResponseEntity<TokenResponse>> getToken(@RequestParam(required = false) String service,
                                                        @RequestBody TokenRequest req) {
        return Mono.fromCallable(() -> tokenService.issue(service, req.getId(), req.getSecret()))
                .map(this::convertToTokenResponse)
                .map(this::ok);
    }

    private TokenResponse convertToTokenResponse(Token token) {
        return new TokenResponse().setToken(token.getToken()).setExpires(toExp(token));
    }

    /**
     * This controller method keeps the client's data model and app model separate.
     * <p>
     * The app will produce an "expires at" time and the client wants the "expires in" representation.
     *
     * @param token point in the future when the token is no longer value
     * @return number of seconds until that point is reached
     */
    private int toExp(Token token) {
        int issued = Math.toIntExact(token.getIssued().toInstant().getLong(ChronoField.INSTANT_SECONDS));
        int expiresAt = Math.toIntExact(token.getExpiresAt().toInstant().getLong(ChronoField.INSTANT_SECONDS));
        return expiresAt - issued;
    }
}

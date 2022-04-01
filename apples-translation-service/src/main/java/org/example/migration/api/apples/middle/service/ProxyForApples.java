package org.example.migration.api.apples.middle.service;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.example.migration.common.backend.Result;
import org.example.migration.common.backend.TokenRequest;
import org.example.migration.common.backend.TokenResponse;
import org.example.migration.common.middle.MiddleApple;
import org.example.migration.common.middle.MiddleResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class ProxyForApples extends BaseProxyService<ProxyForApples.ApplesParams> {

    private static final String TOKEN_PATH = "/services/token";
    private static final String APPLES_PATH = "/services/orchard/apples";
    private static final String TOKEN_PARAM = "token";
    private static final String COUNT_PARAM = "count";
    private final WebClient webClient;

    public ProxyForApples(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public ApplesParams newParams() {
        return new ApplesParams();
    }

    @Override
    public AvailableProxyService whichService() {
        return AvailableProxyService.APPLES;
    }

    // todo send client headers back and forth
    @Override
    public ResponseEntity<MiddleResult<MiddleApple>> serviceRequest(ApplesParams params) {
        String token = getToken();

        String uri = UriComponentsBuilder.fromUriString(backendProperties.getUrl())
                .path(APPLES_PATH)
                .queryParam(TOKEN_PARAM, token)
                .queryParam(COUNT_PARAM, params.getTotal())
                .build().toString();

        log.debug("querying apples backend with url {}", uri);

        ResponseEntity<Result> resultResponseEntity = webClient.get().uri(uri)
                .exchangeToMono(c -> c.toEntity(Result.class))
                .blockOptional().orElseThrow();

        Result body = Objects.requireNonNull(handleBadValue(resultResponseEntity).getBody());
        MiddleResult<MiddleApple> middleResult = new MiddleResult<>();

        // copy count
        middleResult.setCount(body.getCount());
        // copy apples
        for (List<Result.Property> entity : body.getEntity()) {
            MiddleApple apple = copyPropertiesToApple(entity);
            middleResult.getEntities().add(apple);
        }

        return ResponseEntity.ok(middleResult);

    }

    private MiddleApple copyPropertiesToApple(List<Result.Property> entity) {
        MiddleApple apple = new MiddleApple();

        for (Result.Property property : entity) {
            switch (property.getKey()) {
                case "name":
                    apple.setName(property.getValue());
                    break;
                case "color":
                    apple.setColor(property.getValue());
                    break;
            }
        }
        return apple;
    }

    String getToken() {
        ResponseEntity<TokenResponse> tokenResponse = webClient.post().uri(backendProperties.getUrl() + TOKEN_PATH)
                .bodyValue(new TokenRequest().setId(backendProperties.getUsername()).setSecret(backendProperties.getPassword()))
                .exchangeToMono(c -> c.toEntity(TokenResponse.class))
                .blockOptional().orElseThrow();

        handleBadValue(tokenResponse);

        String token = Optional.of(tokenResponse).map(HttpEntity::getBody).map(TokenResponse::getToken).orElse(null);
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "did not get a token back");
        }
        return token;
    }

    @Accessors(chain = true)
    @Data
    public static class ApplesParams implements ProxyServiceParams {
        private MultiValueMap<String, String> map;

        @Override
        public ApplesParams parse(ServerHttpRequest request) {
            map = request.getQueryParams();
            return this;
        }

        public Integer getTotal() {
            try {
                String first = map.getFirst(COUNT_PARAM);
                if (first == null) return null;
                return Integer.valueOf(first);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }
}

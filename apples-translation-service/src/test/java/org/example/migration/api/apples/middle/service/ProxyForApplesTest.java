package org.example.migration.api.apples.middle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.migration.api.apples.middle.property.BackendProperties;
import org.example.migration.common.backend.Result;
import org.example.migration.common.backend.TokenResponse;
import org.example.migration.common.middle.MiddleApple;
import org.example.migration.common.middle.MiddleResult;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ProxyForApplesTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Test
    void test() {
        String tokenResponse = objectMapper.writeValueAsString(new TokenResponse().setToken("success").setExpires(3600));
        String applesResponse = objectMapper.writeValueAsString(new Result()
                .setCount(2)
                .setEntity(List.of(
                        List.of(new Result.Property().setKey("name").setValue("id1"),
                                new Result.Property().setKey("color").setValue("#fff")),
                        List.of(new Result.Property().setKey("name").setValue("id2"),
                                new Result.Property().setKey("color").setValue("#ffe"))
                )));


        // mock back end
        WebClient webClient = WebClient.builder().exchangeFunction((ClientRequest request) -> {
            String path = request.url().getPath();
            ClientResponse.Builder ok = ClientResponse.create(HttpStatus.OK).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            if (path.equals("/services/token")) {
                return Mono.just(ok.body(tokenResponse).build());
            } else if (path.equals("/services/orchard/apples")) {
                return Mono.just(ok.body(applesResponse).build());
            } else {
                return Mono.just(ClientResponse.create(HttpStatus.NOT_FOUND).build());
            }
        }).build();

        ProxyForApples proxyForApples = new ProxyForApples(webClient);
        proxyForApples.setBackendProperties(new BackendProperties());
        ResponseEntity<MiddleResult<MiddleApple>> response = proxyForApples.serviceRequest(new ProxyForApples.ApplesParams().setMap(new LinkedMultiValueMap<>()));

        assertThat(response, is(notNullValue()));
        assertThat(response.getBody(), is(notNullValue()));
        assertThat(response.getBody().getCount(), is(2));
        assertThat(response.getBody().getEntities().size(), is(2));
        assertThat(response.getBody().getEntities().get(0).getName(), is("id1"));
        assertThat(response.getBody().getEntities().get(1).getName(), is("id2"));
        assertThat(response.getBody().getEntities().get(0).getColor(), is("#fff"));
        assertThat(response.getBody().getEntities().get(1).getColor(), is("#ffe"));
    }

}

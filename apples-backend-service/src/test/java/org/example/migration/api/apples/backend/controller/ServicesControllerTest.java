package org.example.migration.api.apples.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.migration.api.apples.backend.BaseTest;
import org.example.migration.api.apples.backend.property.AuthProperties;
import org.example.migration.common.backend.TokenRequest;
import org.example.migration.common.backend.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServicesControllerTest extends BaseTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    AuthProperties authProperties;

    @Test
    void test() {
        EntityExchangeResult<TokenResponse> exchangeResult = webTestClient.post()
                .uri("/services/token")
                .bodyValue(new TokenRequest().setId("username").setSecret("password"))
                .exchange().expectBody(TokenResponse.class).returnResult();

        assertThat(exchangeResult.getStatus(), is(HttpStatus.OK));
        TokenResponse body = exchangeResult.getResponseBody();
        assertThat(body, is(notNullValue()));
        assertThat(body.getExpires(), is(greaterThan(0)));
        assertThat(body.getExpires(), is(lessThanOrEqualTo(authProperties.getTokenTimeout())));
        assertThat(body.getToken(), is(notNullValue()));
        log.info("successfully requested a token without service: {}", body.getToken());
    }

}

package org.example.migration.api.apples.middle.controller;

import org.example.migration.api.apples.middle.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTest extends BaseTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void tmp() {
        // temporary test, just to see if routing works
        EntityExchangeResult<String> resultNested = webTestClient.get().uri("/api/nest/nested").exchange().expectBody(String.class).returnResult();
        EntityExchangeResult<String> resultRoot = webTestClient.get().uri("/api/root").exchange().expectBody(String.class).returnResult();
        assertEquals(resultNested.getRawStatusCode(), 200);
        assertEquals(resultNested.getResponseBody(), "nest");
        assertEquals(resultRoot.getRawStatusCode(), 200);
        assertEquals(resultRoot.getResponseBody(), "root");
    }

}

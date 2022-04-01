package org.example.migration.api.apples.middle.controller;

import org.example.migration.api.apples.middle.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiControllerTest extends BaseTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void empty() {
    }
}

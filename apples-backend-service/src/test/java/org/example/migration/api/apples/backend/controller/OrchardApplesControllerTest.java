package org.example.migration.api.apples.backend.controller;

import org.example.migration.api.apples.backend.BaseTest;
import org.example.migration.common.backend.Result;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrchardApplesControllerTest extends BaseTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void test() {
        EntityExchangeResult<Result> result = webTestClient.mutate().responseTimeout(Duration.ofDays(1)).build().get()
                .uri("/services/orchard/apples?token=1")
                .exchange().expectBody(Result.class).returnResult();
        Result responseBody = result.getResponseBody();
        assertThat(responseBody, is(notNullValue()));
        assertThat(responseBody.getEntity(), is(notNullValue()));
        assertThat(responseBody.getCount(), is(responseBody.getEntity().size()));
        assertThat(responseBody.getCount(), is(greaterThan(0)));
        for (List<Result.Property> properties : responseBody.getEntity()) {
            assertThat(properties, hasSize(greaterThan(0)));
            // assert that properties has an item which has property 'key' which is 'name'
            assertThat(properties, hasItem(which(hasProperty("key", which(is("name"))))));

            // equivalent in java
            assertThat(properties.stream().filter(e -> e.getKey().equals("name")).collect(Collectors.toList()), hasSize(greaterThan(0)));
        }

        System.out.println();
    }

    private <T> Matcher<T> which(Matcher<T> matches) {
        return is(matches);
    }
}

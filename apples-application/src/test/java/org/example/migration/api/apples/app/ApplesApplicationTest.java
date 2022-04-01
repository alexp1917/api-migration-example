package org.example.migration.api.apples.app;

import org.example.migration.api.apples.app.entity.Apple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplesApplicationTest extends BaseTest {
    ParameterizedTypeReference<List<Apple>> appleListType = new ParameterizedTypeReference<>() {
    };

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testGetOneApple() {
        EntityExchangeResult<Apple> result = webTestClient.get()
                .uri("/apples/" + uuids.generate("testGetOne"))
                .exchange().expectBody(Apple.class).returnResult();
        assertThat(result.getStatus(), is(HttpStatus.OK));
        assertThat(result.getResponseBody(), is(notNullValue()));
        assertThat(result.getResponseBody().getName(), is("example"));
    }

    @Test
    void testGetAllApples() {
        EntityExchangeResult<List<Apple>> result = webTestClient.get()
                .uri("/apples")
                .exchange().expectBody(appleListType).returnResult();
        assertThat(result.getStatus(), is(HttpStatus.OK));
        assertThat(result.getResponseBody(), is(notNullValue()));
        assertThat(result.getResponseBody(), hasSize(greaterThan(0)));
    }
}

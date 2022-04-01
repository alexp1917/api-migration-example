package org.example.migration.api.apples.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.example.migration.common.backend.Result;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Slf4j
class ApplesServiceTest {

    ApplesService applesService = new ApplesService();

    @Test
    void test() {
        Result one = applesService.getOne(UUID.randomUUID());
        log.debug("got back one: {}", one);

        assertThat(applesService.getOne(UUID.randomUUID()), is(notNullValue()));
        assertThat(applesService.getAllApples(3), is(notNullValue()));
    }

}

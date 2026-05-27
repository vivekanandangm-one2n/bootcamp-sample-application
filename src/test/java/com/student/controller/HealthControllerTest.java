package com.student.controller;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

@QuarkusTest
class HealthControllerTest {

  @Test
  void testPingEndpoint() {
    RestAssured.given()
        .when().get("/ping")
        .then()
        .statusCode(200)
        .body(CoreMatchers.is("pong"));
  }
}
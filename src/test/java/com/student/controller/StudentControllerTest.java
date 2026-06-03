package com.student.controller;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

@QuarkusTest
@TestHTTPEndpoint(StudentController.class)
public class StudentControllerTest {

  @Test
  public void GET_ListStudents_NoStudentsInDb_Returns200EmptyList() {

    RestAssured.given()
        .when().get()
        .then()
        .statusCode(200)
        .body(CoreMatchers.containsString("[]"));
  }

  @Test
  public void GET_ListStudents_StudentsInDb_Returns200() {

    RestAssured.given()
        .when().get()
        .then()
        .statusCode(200)
        .body("size()", CoreMatchers.is(2))
        .body("[0]", Matchers.hasKey("id"))
        .body("[0]", Matchers.hasValue(1));
  }

  @Test
  public void GET_StudentById_StudentInDb_Returns200() {

    RestAssured.given()
        .when().get("/1")
        .then()
        .statusCode(200)
        .body("id", Matchers.equalTo(1))
        .body("name", Matchers.equalTo("student 1"));
  }

  @Test
  public void GET_StudentById_StudentNotInDb_Returns404() {

    RestAssured.given()
        .when().get("/2")
        .then()
        .statusCode(404);
  }

  @Test
  public void POST_CreateStudent_StudentCreated_Returns200() {
    var requestBody = "{\"name\":\"student 1\"}";

    RestAssured.given()
        .header("Content-Type", "application/json")
        .body(requestBody)
        .when().post()
        .then()
        .statusCode(200)
        .body("name", Matchers.equalTo("student 1"));
  }

  @Test
  public void PUT_UpdateStudent_StudentDoesNotExist_Returns404() {
    var requestBody = "{\"name\":\"student updated\"}";

    RestAssured.given()
        .header("Content-Type", "application/json")
        .body(requestBody)
        .when().put("/1")
        .then()
        .statusCode(404);
  }

  @Test
  public void PUT_UpdateStudent_StudentExist_Returns204() {
    var requestBody = "{\"name\":\"student updated\"}";

    RestAssured.given()
        .header("Content-Type", "application/json")
        .body(requestBody)
        .when().put("/1")
        .then()
        .statusCode(204);
  }

  @Test
  public void DELETE_DeleteStudent_StudentDoesNotExist_Returns404() {

    RestAssured.given()
        .header("Content-Type", "application/json")
        .when().delete("/1")
        .then()
        .statusCode(404);
  }

  @Test
  public void DELETE_DeleteStudent_StudentExists_Returns204() {

    RestAssured.given()
        .header("Content-Type", "application/json")
        .when().delete("/1")
        .then()
        .statusCode(204);
  }
}

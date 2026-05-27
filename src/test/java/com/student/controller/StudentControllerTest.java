package com.student.controller;

import com.student.entity.Student;
import com.student.repository.StudentRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import java.util.Collections;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
@TestHTTPEndpoint(StudentController.class)
public class StudentControllerTest {

  @InjectMock
  StudentRepository studentRepository;

  @Test
  public void givenNoStudents_whenListStudents_thenRespondWithEmptyList() {
    Mockito.when(studentRepository.getStudents()).thenReturn(Collections.emptyList());
    RestAssured.given()
        .when().get()
        .then()
        .statusCode(200)
        .body(CoreMatchers.containsString("[]"));
  }

  @Test
  public void givenStudents_whenListStudents_thenRespondWithAllStudentList() {
    Mockito.when(studentRepository.getStudents())
        .thenReturn(List.of(new Student(1L, "student 1"), new Student(2L, "student 2")));
    RestAssured.given()
        .when().get()
        .then()
        .statusCode(200)
        .body("size()", CoreMatchers.is(2));
  }
}

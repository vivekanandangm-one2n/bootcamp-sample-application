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
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
@TestHTTPEndpoint(StudentController.class)
public class StudentControllerTest {

  @InjectMock
  StudentRepository studentRepository;

  @Test
  public void GET_ListStudents_NoStudentsInDb_Returns200EmptyList() {
    Mockito.when(studentRepository.getStudents()).thenReturn(Collections.emptyList());

    RestAssured.given()
        .when().get()
        .then()
        .statusCode(200)
        .body(CoreMatchers.containsString("[]"));
  }

  @Test
  public void GET_ListStudents_StudentsInDb_Returns200() {
    Mockito.when(studentRepository.getStudents())
        .thenReturn(List.of(new Student(1L, "student 1"), new Student(2L, "student 2")));

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
    Mockito.when(studentRepository.getStudentById(1L))
        .thenReturn(new Student(1L, "student 1"));

    RestAssured.given()
        .when().get("/1")
        .then()
        .statusCode(200)
        .body("id", Matchers.equalTo(1))
        .body("name", Matchers.equalTo("student 1"));
  }

  @Test
  public void GET_StudentById_StudentNotInDb_Returns404() {
    Mockito.when(studentRepository.getStudentById(2L)).thenReturn(null);

    RestAssured.given()
        .when().get("/2")
        .then()
        .statusCode(404);
  }

  @Test
  public void POST_CreateStudent_StudentCreated_Returns200() {
    var student = new Student(1L, "student 1");
    var requestBody = "{\"name\":\"student 1\"}";

    Mockito.doNothing().when(studentRepository).createStudent(student);

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

    Mockito.doNothing().when(studentRepository).updateStudent(1L, "student updated");
    Mockito.when(studentRepository.getStudentById(1L)).thenReturn(null);

    RestAssured.given()
        .header("Content-Type", "application/json")
        .body(requestBody)
        .when().put("/1")
        .then()
        .statusCode(404);
  }

  @Test
  public void PUT_UpdateStudent_StudentExist_Returns204() {
    var student = new Student(1L, "student 1");
    var requestBody = "{\"name\":\"student updated\"}";

    Mockito.doNothing().when(studentRepository).updateStudent(1L, "student updated");
    Mockito.when(studentRepository.getStudentById(1L)).thenReturn(student);

    RestAssured.given()
        .header("Content-Type", "application/json")
        .body(requestBody)
        .when().put("/1")
        .then()
        .statusCode(204);
  }

  @Test
  public void DELETE_DeleteStudent_StudentDoesNotExist_Returns404() {

    Mockito.doNothing().when(studentRepository).deleteStudent(1L);
    Mockito.when(studentRepository.getStudentById(1L)).thenReturn(null);

    RestAssured.given()
        .header("Content-Type", "application/json")
        .when().delete("/1")
        .then()
        .statusCode(404);
  }

  @Test
  public void DELETE_DeleteStudent_StudentExists_Returns204() {
    var student = new Student(1L, "student 1");

    Mockito.doNothing().when(studentRepository).deleteStudent(1L);
    Mockito.when(studentRepository.getStudentById(1L)).thenReturn(student);

    RestAssured.given()
        .header("Content-Type", "application/json")
        .when().delete("/1")
        .then()
        .statusCode(204);
  }
}

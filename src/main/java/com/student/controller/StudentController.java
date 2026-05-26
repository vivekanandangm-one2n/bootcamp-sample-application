package com.student.controller;

import com.student.entity.Student;
import com.student.request.CreateStudentRequest;
import com.student.request.UpdateStudentRequest;
import com.student.service.StudentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;

@Path("/api/v1/students")
public class StudentController {

  @Inject
  StudentService studentService;

  @GET
  public void getStudents() {
    List<Student> students = studentService.getStudents();
  }

  @GET
  @Path("/{id}")
  public void getStudentById(@PathParam("id") Long id) {
    Student student = studentService.getStudentById(id);
  }

  @POST
  public void createStudent(CreateStudentRequest request) {
    // TODO implement id fetching logic

    Student student = studentService.addStudent(new Student(null, request.getName()));
  }

  @PUT
  public void updateStudent(UpdateStudentRequest request) {
    studentService.updateStudent(request.getId(), request.getName());
  }

  @DELETE
  @Path("/{id}")
  public void deleteStudent(@PathParam("id") Long id) {
    studentService.deleteStudentById(id);
  }
}

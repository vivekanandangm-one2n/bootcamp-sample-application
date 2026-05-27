package com.student.controller;

import com.student.entity.Student;
import com.student.request.CreateStudentRequest;
import com.student.request.UpdateStudentRequest;
import com.student.service.StudentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1/students")
public class StudentController {

  @Inject
  StudentService studentService;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStudents() {
    List<Student> students = studentService.getStudents();

    return Response.ok(students).build();
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getStudentById(@PathParam("id") Long id) {
    Student student = studentService.getStudentById(id);

    if (student == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    return Response.ok(student).build();
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createStudent(CreateStudentRequest request) {
    Student student = studentService.addStudent(new Student(request.getName()));

    return Response.ok(student).build();
  }

  @PUT
  @Path("/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public void updateStudent(@PathParam("id") Long id, UpdateStudentRequest request) {
    // TODO check if the user with id exists
    studentService.updateStudent(id, request.getName());
  }

  @DELETE
  @Path("/{id}")
  public void deleteStudent(@PathParam("id") Long id) {
    // TODO check if the user with id exists
    studentService.deleteStudentById(id);
  }
}

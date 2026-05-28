package com.student.service;

import com.student.entity.Student;
import com.student.repository.StudentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class StudentService {

  @Inject
  StudentRepository studentRepository;

  public List<Student> getStudents() {
    return studentRepository.getStudents();
  }

  public Student getStudentById(Long id) {
    return studentRepository.getStudentById(id);
  }

  public Student addStudent(Student student) {
    studentRepository.createStudent(student);
    return student;
  }

  public void deleteStudentById(Long id) {
    studentRepository.deleteStudent(id);
  }

  public void updateStudent(Long id, String name) {
    studentRepository.updateStudent(id, name);
  }
}

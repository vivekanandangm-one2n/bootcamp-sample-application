package com.student.repository;

import com.student.entity.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {

  public List<Student> getStudents() {
    return findAll().list();
  }

  public Student getStudentById(Long id) {
    return findById(id);
  }

  @Transactional
  public void createStudent(Student student) {
    persist(student);
  }

  @Transactional
  public void updateStudent(Long id, String name) {
    Student student = findById(id);
    student.setId(id);
    student.setName(name);
  }
}

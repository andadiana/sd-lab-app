package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Student;

import java.util.List;

public interface StudentService {

    public List<Student> getAllStudents();
    public Student getStudentById(int id);
    public Student createStudent(Student student);
    public Student updateStudent(Student student);
    public boolean deleteStudent(int id);
}

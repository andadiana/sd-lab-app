package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Student;

import java.util.List;

public interface StudentService {

    public List<Student> getAllStudents();
    public Student getStudentById(int id);
    public String createStudent(Student student);
    public Student updateStudent(Student student);
    public void deleteStudent(int id);
    public boolean isValid(Student student);
    public Student getStudentByEmail(String email);
    public void updatePassword(Student student, String password);
}

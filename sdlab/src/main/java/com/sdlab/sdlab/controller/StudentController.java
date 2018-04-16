package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Student;
import com.sdlab.sdlab.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = GET)
    public List<Student> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        for (Student student: students) {
            System.out.println(student);
        }
        return students;
    }

    @RequestMapping(method = GET, value = "/{studentId}")
    public ResponseEntity getStudentById(@PathVariable Integer studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            //student not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @RequestMapping(method = POST)
    public ResponseEntity createStudent(@RequestBody Student student) {
        //TODO maybe find a diff method for exception handling?
        //check if student already exists
        Student student1 = studentService.getStudentByEmail(student.getEmail());
        if (student1 != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student email already exists!");
        }
        if (studentService.isValid(student)) {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.status(HttpStatus.OK).body(createdStudent);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid student structure!");
    }

    @RequestMapping(method = PUT, value = "/{studentId}")
    public ResponseEntity updateStudent(@PathVariable Integer studentId, @RequestBody Student student) {
        Student student1 = studentService.getStudentById(studentId);
        if (student1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found!");
        }
        if (studentService.isValid(student)) {
            student.setId(studentId);
            studentService.updateStudent(student);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid student structure!");
    }

    @RequestMapping(method = DELETE, value = "/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Integer studentId) {

        studentService.deleteStudent(studentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}

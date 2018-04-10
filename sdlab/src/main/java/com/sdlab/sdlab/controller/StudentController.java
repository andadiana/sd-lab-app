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
    public Student getStudentById(@PathVariable Integer studentId) {
        Student student = studentService.getStudentById(studentId);
        return student;
    }

    @RequestMapping(method = POST)
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        //TODO check if lab is valid (add method in studentservice

//        if (personService.isValid(person)) {
//            personRepository.persist(person);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }
//        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @RequestMapping(method = PUT, value = "/{studentId}")
    public ResponseEntity<String> updateStudent(@RequestBody Student student) {
        //TODO check if lab is valid (add method in studentservice

//        if (personService.isValid(person)) {
//            personRepository.persist(person);
//            return ResponseEntity.status(HttpStatus.CREATED).build();
//        }
//        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
        studentService.updateStudent(student);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @RequestMapping(method = DELETE, value = "/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Integer studentId) {

        // true -> can delete
        // false -> cannot delete, f.e. is FK reference somewhere
        boolean wasOk = studentService.deleteStudent(studentId);

        //TODO do stuff if !ok
//        if (!wasOk) {
//            // will write to user which item couldn't be deleted
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            model.addAttribute("item", item);
//            return "items/error";
//        }
//
//        return "redirect:/items";

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}

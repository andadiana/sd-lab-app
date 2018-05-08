package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.dto.request.PasswordUpdateDTO;
import com.sdlab.sdlab.dto.request.StudentRequestDTO;
import com.sdlab.sdlab.dto.response.StudentCreationResponseDTO;
import com.sdlab.sdlab.dto.response.StudentResponseDTO;
import com.sdlab.sdlab.model.Student;
import com.sdlab.sdlab.service.StudentService;
import com.sdlab.sdlab.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = GET)
    public List<StudentResponseDTO> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        List<StudentResponseDTO> studentsDTO = students.stream()
                .map(s -> modelMapper.map(s, StudentResponseDTO.class)).collect(Collectors.toList());
        return studentsDTO;
    }

    @RequestMapping(method = GET, value = "/{studentId}")
    public ResponseEntity getStudentById(@PathVariable Integer studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            //student not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(student, StudentResponseDTO.class));
    }

    @RequestMapping(method = POST)
    public ResponseEntity createStudent(@Validated @RequestBody StudentRequestDTO studentDTO) {
        //check if student already exists
        Student student1 = studentService.getStudentByEmail(studentDTO.getEmail());
        if (student1 != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student email already exists!");
        }
        Student student = modelMapper.map(studentDTO, Student.class);
        if (studentService.isValid(student)) {
            String token = studentService.createStudent(student);
            StudentCreationResponseDTO responseDTO = new StudentCreationResponseDTO();
            responseDTO.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid student structure!");
    }

    @RequestMapping(method = PUT, value = "/{studentId}")
    public ResponseEntity updateStudent(@PathVariable Integer studentId, @Validated @RequestBody StudentRequestDTO studentDTO) {
        Student student1 = studentService.getStudentById(studentId);
        if (student1 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found!");
        }
        student1 = studentService.getStudentByEmail(studentDTO.getEmail());
        if (student1 != null && student1.getId() != studentId) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student email already exists!");
        }
        Student student = modelMapper.map(studentDTO, Student.class);
        if (studentService.isValid(student)) {
            student.setId(studentId);
            studentService.updateStudent(student);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid student structure!");
    }

    @RequestMapping(method = PUT, value = "/{studentId}/password")
    @PreAuthorize("#userId == principal.id")
    public ResponseEntity updatePassword(@PathVariable Integer studentId,
                                         @Validated @RequestBody PasswordUpdateDTO passwordUpdateDTO) {

        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found!");
        }
        try {
            userService.logIn(student.getEmail(), passwordUpdateDTO.getOldPassword());
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
        }
        studentService.updatePassword(student, passwordUpdateDTO.getNewPassword());
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }


    @RequestMapping(method = DELETE, value = "/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable Integer studentId) {

        studentService.deleteStudent(studentId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}

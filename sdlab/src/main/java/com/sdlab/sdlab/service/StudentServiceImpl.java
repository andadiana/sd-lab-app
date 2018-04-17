package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Role;
import com.sdlab.sdlab.model.Student;
import com.sdlab.sdlab.repository.StudentRepository;
import com.sdlab.sdlab.util.EmailValidator;
import com.sdlab.sdlab.util.GroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(int id) {
        Optional<Student> res = studentRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    @Override
    public Student createStudent(Student student) {
        student.setRole(Role.STUDENT);
        student.setPasswordSet(false);
        student.setId(0);
        System.out.println("Student service create: " + student);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        Student studentToUpdate = studentRepository.getOne(student.getId());
        if (student.getGroup() != null) {
            studentToUpdate.setGroup(student.getGroup());
        }
        if (student.getHobby() != null) {
            studentToUpdate.setHobby(student.getHobby());
        }
        if (student.getEmail() != null) {
            studentToUpdate.setEmail(student.getEmail());
        }
        if (student.getName() != null) {
            studentToUpdate.setName(student.getName());
        }
        if (student.getPassword() != null) {
            studentToUpdate.setPassword(student.getPassword());
        }
        return studentRepository.save(studentToUpdate);
    }

    @Override
    public void deleteStudent(int id) {
        Optional<Student> res = studentRepository.findById(id);
        if (res.isPresent()) {
            studentRepository.deleteById(id);
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        Optional<Student> res = studentRepository.getStudentByEmail(email);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    @Override
    public boolean isValid(Student student) {
        //check email and group format
        if (EmailValidator.validate(student.getEmail()) && GroupValidator.validate(student.getGroup())) {
            return true;
        }
        return false;
    }
}

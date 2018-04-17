package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Role;
import com.sdlab.sdlab.model.Student;
import com.sdlab.sdlab.repository.StudentRepository;
import com.sdlab.sdlab.util.EmailValidator;
import com.sdlab.sdlab.util.GroupValidator;
import com.sdlab.sdlab.util.PasswordEncrypter;
import com.sdlab.sdlab.util.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    private PasswordEncrypter encrypter;

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
        String token = generateToken();
        System.out.println("\n\nToken is: \n\n" + token);
        student.setPassword(encryptPassword(token));
        System.out.println("Student service create: " + student);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        Student studentToUpdate = studentRepository.getOne(student.getId());
        student.setRole(Role.STUDENT);
        student.setPasswordSet(studentToUpdate.isPasswordSet());
        student.setPassword(studentToUpdate.getPassword());
        return studentRepository.save(student);
    }

    @Override
    public void updatePassword(Student student, String password) {
        student.setPassword(encryptPassword(password));
        student.setPasswordSet(true);
        studentRepository.save(student);
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
        if (student.getEmail() != null && EmailValidator.validate(student.getEmail())) {
            if (student.getGroup() != null && GroupValidator.validate(student.getGroup())){
                return true;
            }
        }
        return false;
    }

    private String generateToken() {
        return tokenGenerator.generateToken();
    }

    private String encryptPassword(String password) {
        return encrypter.encrypt(password);
    }
}

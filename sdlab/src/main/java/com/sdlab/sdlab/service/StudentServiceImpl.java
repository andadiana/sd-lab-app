package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Student;
import com.sdlab.sdlab.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {


    @Autowired
    private StudentRepository studentRepository;


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        Optional<Student> res = studentRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    public Student createStudent(Student student) {
        System.out.println("Student service create: " + student);
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        Student studentToUpdate = studentRepository.getOne(student.getId());
        studentToUpdate.setName(student.getName());
        studentToUpdate.setEmail(student.getName());
        studentToUpdate.setPassword(student.getPassword());
        studentToUpdate.setGroup(student.getGroup());
        studentToUpdate.setHobby(student.getHobby());
        return studentRepository.save(studentToUpdate);
    }

    public boolean deleteStudent(int id) {
        Optional<Student> studentToDelete = studentRepository.findById(id);
        //TODO check if student exists
        studentRepository.delete(studentToDelete.get());
        return true;
    }
}

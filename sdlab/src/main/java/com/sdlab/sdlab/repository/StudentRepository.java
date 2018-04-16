package com.sdlab.sdlab.repository;

import com.sdlab.sdlab.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    public Optional<Student> getStudentByEmail(String email);
}

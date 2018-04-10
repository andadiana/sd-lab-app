package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Teacher;
import com.sdlab.sdlab.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Teacher getTeacherById(int id) {
        Optional<Teacher> res = teacherRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }
}

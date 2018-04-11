package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.User;
import com.sdlab.sdlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository teacherRepository;

    public User getById(int id) {
        Optional<User> res = teacherRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }
}

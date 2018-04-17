package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Role;
import com.sdlab.sdlab.model.User;
import com.sdlab.sdlab.repository.UserRepository;
import com.sdlab.sdlab.util.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncrypter encrypter;

    public User getById(int id) {
        Optional<User> res = userRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        else {
            return null;
        }
    }

    public Role logIn(String email, String password) throws LoginException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new LoginException("User with given email does not exist!");
        }
        if (checkPassword(password, user.getPassword())) {
            return user.getRole();
        }
        throw new LoginException("Incorrect password!");
    }

    @Override
    public boolean isPasswordSet(User user) {
        User u = userRepository.findByEmail(user.getEmail());
        return u.isPasswordSet();
    }

    private boolean checkPassword(String givenPass, String actualPass) {
        //PasswordEncrypter encrypter = new PasswordEncrypterMD5();
        String encryptedPass = encrypter.encrypt(givenPass);
        if (encryptedPass.equals(actualPass)) {
            return true;
        }
        return false;
    }
}

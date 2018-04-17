package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.model.Role;
import com.sdlab.sdlab.model.User;
import com.sdlab.sdlab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = POST)
    public ResponseEntity logIn(@RequestBody User user) {
        try {
            Role role = userService.logIn(user.getEmail(), user.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(role);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
        }

    }
}

package com.sdlab.sdlab.controller;

import com.sdlab.sdlab.dto.request.LoginRequestDTO;
import com.sdlab.sdlab.dto.response.LoginResponseDTO;
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

import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = POST)
    public ResponseEntity logIn(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Role role = userService.logIn(loginRequest.getEmail(), loginRequest.getPassword());
            User user = userService.getByEmail(loginRequest.getEmail());
            LoginResponseDTO responseDTO = new LoginResponseDTO();
            responseDTO.setRole(role.toString());
            responseDTO.setUserId(user.getId());
            if (userService.isPasswordSet(loginRequest.getEmail())) {
                responseDTO.setPasswordSet(true);
            }
            else {
                responseDTO.setPasswordSet(true);
            }
            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (LoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
        }

    }
}

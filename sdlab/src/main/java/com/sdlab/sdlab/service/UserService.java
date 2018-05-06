package com.sdlab.sdlab.service;

import com.sdlab.sdlab.model.Role;
import com.sdlab.sdlab.model.User;

import javax.security.auth.login.LoginException;

public interface UserService {

    public User getById(int id);
    public Role logIn(String email, String password) throws LoginException;
    public boolean isPasswordSet(String email);
    public User getByEmail(String email);
}

package com.sdlab.sdlab.config;

import com.sdlab.sdlab.model.Role;
import com.sdlab.sdlab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("\n\nEmail : " +email);
        System.out.println("\n\nPass: " + password);

        try {
            Role role = userService.logIn(email, password);

            System.out.println("\n\nrole: " +role);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(email, password, authorities);
            return token;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}

package com.sdlab.sdlab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.authenticationProvider(customAuthenticationProvider);
    }

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/students").hasAnyRole("ADMIN", "STUDENT")
                .antMatchers(HttpMethod.POST,"/students").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/students").hasRole("ADMIN")

                .antMatchers(HttpMethod.POST, "/labs").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/labs/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/labs/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/labs").hasAnyRole("ADMIN", "STUDENT")
                .antMatchers(HttpMethod.GET,"/assignments").hasAnyRole("ADMIN", "STUDENT")
                .antMatchers(HttpMethod.POST,"/assignments").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/assignments").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/assignments").hasRole("ADMIN")
                .antMatchers("/attendance").hasRole("ADMIN")
                .antMatchers("/submissions").hasAnyRole("ADMIN", "STUDENT")
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                ;

    }
}


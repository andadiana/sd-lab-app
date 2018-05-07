package com.sdlab.sdlab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
//        authenticationMgr.inMemoryAuthentication()
//                .withUser("admin").password("{noop}123").roles("ADMIN")
//                .and()
//                .withUser("student").password("{noop}123").roles("STUDENT");

        authenticationMgr.authenticationProvider(customAuthenticationProvider);
    }

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().httpBasic().and().authorizeRequests()
                .antMatchers("/students").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/labs").hasAnyRole("ADMIN", "STUDENT")
                .antMatchers(HttpMethod.POST, "/labs").hasRole("ADMIN")
                .antMatchers("/assignments").hasAnyRole("ADMIN", "STUDENT")
                .antMatchers("/attendance").hasRole("ADMIN")
                .antMatchers("/submissions").hasAnyRole("ADMIN", "STUDENT")
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                ;

//        http.csrf().disable().httpBasic().and().authorizeRequests()
//                .antMatchers("/students").permitAll()
//                .antMatchers("/labs").permitAll()
//                .antMatchers("/assignments").permitAll()
//                .antMatchers("/attendance").permitAll()
//                .antMatchers("/submissions").permitAll()
//                .antMatchers("/login").permitAll()
//        ;
    }
}

